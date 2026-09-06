package com.smartmenu.controller;

import com.smartmenu.model.CustomerSession;
import com.smartmenu.model.Feedback;
import com.smartmenu.model.MenuItem;
import com.smartmenu.model.Order;
import com.smartmenu.model.Table;
import com.smartmenu.repository.CustomerSessionRepository;
import com.smartmenu.repository.FeedbackRepository;
import com.smartmenu.repository.MenuItemRepository;
import com.smartmenu.repository.OrderRepository;
import com.smartmenu.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Public Menu Controller - No authentication required
 * Allows customers to view menu without logging in
 *
 * NOTE: All entity IDs (Table, MenuItem, Order) are now Long
 * because we migrated from MongoDB (String ObjectId) to
 * PostgreSQL (Long auto-increment). deviceId stays a String
 * because it's a client-generated identifier, not a DB key.
 */
@RestController
@RequestMapping("/api/public")
public class PublicMenuController {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CustomerSessionRepository customerSessionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * GET /api/public/table/{tableId}
     * Get table information (for customer view)
     */
    @GetMapping("/table/{tableId}")
    public ResponseEntity<?> getTableInfo(@PathVariable Long tableId) {
        Optional<Table> tableOpt = tableRepository.findById(tableId);

        if (!tableOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Table table = tableOpt.get();
        Map<String, Object> response = new HashMap<>();
        response.put("id", table.getId());
        response.put("tableNumber", table.getTableNumber());
        response.put("userId", table.getUserId());
        response.put("qrCodeId", table.getQrCodeId());
        response.put("qrCodeUrl", table.getQrCodeUrl());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/public/menu/{tableId}
     * Get menu items for a specific table's restaurant
     */
    @GetMapping("/menu/{tableId}")
    public ResponseEntity<?> getMenuForTable(
            @PathVariable Long tableId,
            @RequestParam(required = false) String deviceId
    ) {
        // Get table to find the userId (tenant)
        Optional<Table> tableOpt = tableRepository.findById(tableId);

        if (!tableOpt.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Table not found");
            return ResponseEntity.badRequest().body(error);
        }

        Table table = tableOpt.get();
        String userId = table.getUserId(); // was calling undefined getUserId() on the controller itself

        // Track customer session if deviceId provided
        if (deviceId != null && !deviceId.isEmpty()) {
            trackCustomerSession(deviceId, tableId, userId);
        }

        // Get all available menu items for this restaurant
        List<MenuItem> menuItems = menuItemRepository.findByUserIdAndAvailableTrue(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("tableId", tableId);
        response.put("tableNumber", table.getTableNumber());
        response.put("menuItems", menuItems);
        response.put("totalItems", menuItems.size());

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/public/session
     * Track or update customer session
     */
    @PostMapping("/session")
    public ResponseEntity<?> trackSession(@RequestBody Map<String, String> sessionData) {
        String deviceId = sessionData.get("deviceId");
        String tableIdStr = sessionData.get("tableId");
        String customerName = sessionData.get("customerName");
        String customerPhone = sessionData.get("customerPhone");

        if (deviceId == null || tableIdStr == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "deviceId and tableId are required");
            return ResponseEntity.badRequest().body(error);
        }

        Long tableId;
        try {
            tableId = Long.parseLong(tableIdStr);
        } catch (NumberFormatException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "tableId must be a valid number");
            return ResponseEntity.badRequest().body(error);
        }

        // Get table to find userId
        Optional<Table> tableOpt = tableRepository.findById(tableId);
        if (!tableOpt.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Table not found");
            return ResponseEntity.badRequest().body(error);
        }

        String userId = tableOpt.get().getUserId();

        // Find or create session
        Optional<CustomerSession> existingSession = customerSessionRepository.findByDeviceId(deviceId);
        CustomerSession session;

        if (existingSession.isPresent()) {
            session = existingSession.get();
            session.setVisitCount(session.getVisitCount() + 1);
            session.setLastVisit(LocalDateTime.now());
            session.setTableId(tableId);
            session.setUpdatedAt(LocalDateTime.now());

            if (customerName != null) session.setCustomerName(customerName);
            if (customerPhone != null) session.setCustomerPhone(customerPhone);
        } else {
            session = new CustomerSession(deviceId, tableId, userId);
            if (customerName != null) session.setCustomerName(customerName);
            if (customerPhone != null) session.setCustomerPhone(customerPhone);
        }

        session = customerSessionRepository.save(session);

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("visitCount", session.getVisitCount());
        response.put("isReturningCustomer", session.getVisitCount() > 1);
        response.put("customerName", session.getCustomerName());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/public/session/{deviceId}
     * Get customer session info
     */
    @GetMapping("/session/{deviceId}")
    public ResponseEntity<?> getSession(@PathVariable String deviceId) {
        Optional<CustomerSession> sessionOpt = customerSessionRepository.findByDeviceId(deviceId);

        if (!sessionOpt.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No session found");
            response.put("isReturningCustomer", "false");
            return ResponseEntity.ok(response);
        }

        CustomerSession session = sessionOpt.get();
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("visitCount", session.getVisitCount());
        response.put("isReturningCustomer", session.getVisitCount() > 1);
        response.put("customerName", session.getCustomerName());
        response.put("lastVisit", session.getLastVisit());

        return ResponseEntity.ok(response);
    }

    // Helper method to track customer session
    private void trackCustomerSession(String deviceId, Long tableId, String userId) {
        Optional<CustomerSession> existingSession = customerSessionRepository.findByDeviceId(deviceId);

        if (existingSession.isPresent()) {
            CustomerSession session = existingSession.get();
            session.setVisitCount(session.getVisitCount() + 1);
            session.setLastVisit(LocalDateTime.now());
            session.setTableId(tableId);
            session.setUpdatedAt(LocalDateTime.now());
            customerSessionRepository.save(session);
        } else {
            CustomerSession newSession = new CustomerSession(deviceId, tableId, userId);
            customerSessionRepository.save(newSession);
        }
    }

    /**
     * POST /api/public/order
     * Place a new order from customer
     */
    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> orderRequest) {
        try {
            String tableIdStr = String.valueOf(orderRequest.get("tableId"));
            String deviceId = (String) orderRequest.get("deviceId");
            String customerName = (String) orderRequest.get("customerName");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) orderRequest.get("items");

            // Validate
            if (tableIdStr == null || tableIdStr.equals("null") || items == null || items.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Table ID and items are required");
                return ResponseEntity.badRequest().body(error);
            }

            Long tableId;
            try {
                tableId = Long.parseLong(tableIdStr);
            } catch (NumberFormatException e) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "tableId must be a valid number");
                return ResponseEntity.badRequest().body(error);
            }

            // Get table info
            Optional<Table> tableOpt = tableRepository.findById(tableId);
            if (!tableOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Table table = tableOpt.get();

            // Create order
            Order order = new Order();
            order.setUserId(table.getUserId());
            order.setTableId(tableId);
            order.setTableNumber(table.getTableNumber());
            order.setDeviceId(deviceId);
            order.setCustomerName(customerName);
            order.setStatus(Order.OrderStatus.PENDING);

            // Generate order number
            String orderNumber = generateOrderNumber();
            order.setOrderNumber(orderNumber);

            // Process order items
            List<Order.OrderItem> orderItems = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (Map<String, Object> item : items) {
                Order.OrderItem orderItem = new Order.OrderItem();
                orderItem.setMenuItemId(String.valueOf(item.get("id")));
                orderItem.setMenuItemName((String) item.get("name"));
                orderItem.setPrice(BigDecimal.valueOf(((Number) item.get("price")).doubleValue()));
                orderItem.setQuantity(((Number) item.get("quantity")).intValue());
                orderItem.setSpecialInstructions((String) item.get("specialInstructions"));

                orderItems.add(orderItem);
                total = total.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            }

            order.setItems(orderItems);
            order.setSubtotal(total);
            order.setTotal(total);
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            // Save order
            order = orderRepository.save(order);

            // Update customer session if customerName provided
            if (customerName != null && deviceId != null) {
                Optional<CustomerSession> sessionOpt = customerSessionRepository.findByDeviceId(deviceId);
                if (sessionOpt.isPresent()) {
                    CustomerSession session = sessionOpt.get();
                    session.setCustomerName(customerName);
                    customerSessionRepository.save(session);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("orderNumber", orderNumber);
            response.put("orderId", order.getId());
            response.put("status", order.getStatus().toString());
            response.put("message", "Oda imefanikiwa! Order placed successfully!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to place order: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * GET /api/public/order/{orderNumber}
     * Track order status
     */
    @GetMapping("/order/{orderNumber}")
    public ResponseEntity<?> getOrderStatus(@PathVariable String orderNumber) {
        Optional<Order> orderOpt = orderRepository.findByOrderNumber(orderNumber);

        if (!orderOpt.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Order not found");
            return ResponseEntity.notFound().build();
        }

        Order order = orderOpt.get();
        return ResponseEntity.ok(order);
    }

    /**
     * POST /api/public/feedback
     * Submit customer feedback
     */
    @PostMapping("/feedback")
    public ResponseEntity<?> submitFeedback(@RequestBody Map<String, Object> feedbackRequest) {
        try {
            String orderNumber = (String) feedbackRequest.get("orderNumber");
            Integer rating = (Integer) feedbackRequest.get("rating");
            String comments = (String) feedbackRequest.get("comments");

            // Find the order
            Optional<Order> orderOpt = orderRepository.findByOrderNumber(orderNumber);
            if (!orderOpt.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Order not found");
                return ResponseEntity.notFound().build();
            }

            Order order = orderOpt.get();

            // Create feedback (using existing Feedback model)
            Feedback feedback = new Feedback();
            feedback.setUserId(order.getUserId());
            feedback.setOrderId(order.getId());
            feedback.setOrderNumber(orderNumber);
            feedback.setTableNumber(order.getTableNumber());
            feedback.setTotalAmount(order.getTotal().doubleValue());
            feedback.setRating(rating);
            feedback.setComments(comments);
            feedback.setCreatedAt(LocalDateTime.now());

            // Save feedback
            feedbackRepository.save(feedback);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Asante! Thank you for your feedback!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to submit feedback: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    // Generate unique order number
    private String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        int random = (int) (Math.random() * 1000);
        return "ORD" + timestamp + String.format("%03d", random);
    }
}