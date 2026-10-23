import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './LandingPage.css';

export default function LandingPage() {
  const [activeFaq, setActiveFaq] = useState(null);
  const [selectedCategory, setSelectedCategory] = useState('all');

  const toggleFaq = (index) => {
    setActiveFaq(activeFaq === index ? null : index);
  };

  const demoItems = [
    {
      id: 1,
      category: 'mains',
      name: 'Zanzibar Coconut Seafood Platter',
      swahiliName: 'Chakula cha Bahari cha Nazi Zanzibar',
      price: '38,000 TZS',
      tag: "Chef's Special",
      tagColor: 'tag-gold',
      desc: 'Fresh grilled prawns, calamari, and red snapper simmered in rich spiced coconut cream with aromatic saffron pilau.',
      icon: '🦐'
    },
    {
      id: 2,
      category: 'mains',
      name: 'Prime Serengeti Beef Nyama Choma',
      swahiliName: 'Nyama Choma ya Serengeti',
      price: '28,000 TZS',
      tag: 'Bestseller',
      tagColor: 'tag-red',
      desc: 'Charcoal-grilled tender grass-fed beef served with fresh kachumbari salad and golden crispy ugali fries.',
      icon: '🥩'
    },
    {
      id: 3,
      category: 'starters',
      name: 'Swahili Samosa Trio & Tangawizi Dip',
      swahiliName: 'Sambusa za Nyama & Mchuzi wa Tangawizi',
      price: '12,000 TZS',
      tag: 'Crispy & Warm',
      tagColor: 'tag-blue',
      desc: 'Golden handmade pastry pockets stuffed with minced spiced beef and herbs, accompanied by tangy tamarind chutney.',
      icon: '🥟'
    },
    {
      id: 4,
      category: 'drinks',
      name: 'Kilimanjaro Passion & Ginger Cooler',
      swahiliName: 'Kinywaji cha Pasheni na Tangawizi',
      price: '9,500 TZS',
      tag: 'Refreshing',
      tagColor: 'tag-green',
      desc: 'Pressed tropical passion fruit blended with crushed ginger root, fresh wild mint, and sparkling highland water.',
      icon: '🍹'
    }
  ];

  const filteredDemo = selectedCategory === 'all'
    ? demoItems
    : demoItems.filter(item => item.category === selectedCategory);

  const faqs = [
    {
      q: 'Do customers need to download an application from the Play Store or App Store?',
      a: 'No app download is needed. Customers simply scan the QR code using their iPhone or Android camera, and the interactive digital menu opens instantaneously in their standard web browser.'
    },
    {
      q: 'Can I update prices, daily specials, and 86 sold-out dishes on the fly?',
      a: 'Yes! Any update made in your SmartMenu admin dashboard updates the customer menu in real time without needing to reprint anything.'
    },
    {
      q: 'Does SmartMenu support both English and Swahili?',
      a: 'Yes. The system has built-in bilingual support, allowing international tourists and local diners to browse and order comfortably in their preferred language.'
    },
    {
      q: 'How does the kitchen receive customer orders?',
      a: 'Orders flow instantly to the live Kitchen Order Dashboard. Chefs and waitstaff can track progress with real-time status transitions: Pending → Confirmed → Preparing → Served.'
    },
    {
      q: 'How does the customer feedback shield work?',
      a: 'After ordering or completing their meal, guests are prompted to leave a quick 5-star rating and comment. Positive reviews are celebrated, while constructive feedback reaches the restaurant manager directly before the customer posts on Google Reviews or TripAdvisor.'
    }
  ];

  return (
    <div className="landing-root">
      {/* Top Banner */}
      <div className="landing-top-bar">
        <div className="landing-container top-bar-content">
          <span>🇹🇿 <strong>Engineered for African Hospitality</strong> — Turn tables faster & delight your guests.</span>
          <div className="top-bar-links">
            <a href="https://api-smartmenu.techwareafrica.tech/swagger-ui.html" target="_blank" rel="noopener noreferrer" className="top-swagger-badge">
              <i className="fa-solid fa-code"></i> Developer API
            </a>
            <a href="https://www.techwareafrica.tech/products/smartmenu-qr" target="_blank" rel="noopener noreferrer">
              TechWare Africa
            </a>
          </div>
        </div>
      </div>

      {/* Main Navigation */}
      <header className="landing-header">
        <div className="landing-container header-inner">
          <Link to="/" className="landing-logo">
            <div className="logo-badge">
              <i className="fa-solid fa-qrcode"></i>
            </div>
            <div className="logo-text">
              <span className="logo-title">SmartMenu<span className="logo-accent">QR</span></span>
              <span className="logo-subtitle">by TechWare Africa</span>
            </div>
          </Link>

          <nav className="landing-nav">
            <a href="#features">Features</a>
            <a href="#how-it-works">How It Works</a>
            <a href="#demo">Live Demo</a>
            <a href="#roi">Benefits</a>
            <a href="#pricing">Pricing</a>
            <a href="#faq">FAQ</a>
          </nav>

          <div className="header-actions">
            <Link to="/login" className="btn-signin">
              Sign In
            </Link>
            <Link to="/register" className="btn-primary-gradient">
              Get Started Free <i className="fa-solid fa-arrow-right"></i>
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="landing-hero">
        <div className="landing-container hero-grid">
          <div className="hero-text-col">
            <div className="hero-badge">
              <span className="badge-pulse"></span>
              <span>Kitchen 4.0 & Contactless Dining Suite</span>
            </div>

            <h1 className="hero-heading">
              The Intelligent <span className="text-gradient">QR Menu</span> & Instant Ordering Suite
            </h1>

            <p className="hero-subtext">
              Eliminate paper reprint costs and delight your diners. Guests scan table QR codes, view vibrant photos, customize dishes, and place orders directly from their phones.
            </p>

            <div className="hero-cta-group">
              <Link to="/register" className="hero-btn-primary">
                <i className="fa-solid fa-rocket"></i> Start Free Restaurant Trial
              </Link>
              <Link to="/customer-menu?table=1" className="hero-btn-secondary">
                <i className="fa-solid fa-mobile-screen"></i> Launch Customer Demo
              </Link>
            </div>

            <div className="hero-perks">
              <div className="perk-item">
                <i className="fa-solid fa-circle-check"></i>
                <span>Zero app installs</span>
              </div>
              <div className="perk-item">
                <i className="fa-solid fa-circle-check"></i>
                <span>Instant QR generation</span>
              </div>
              <div className="perk-item">
                <i className="fa-solid fa-circle-check"></i>
                <span>Real-time kitchen tracking</span>
              </div>
            </div>
          </div>

          {/* Hero Phone Mockup Simulator */}
          <div className="hero-visual-col" id="demo">
            <div className="phone-mockup">
              <div className="phone-speaker"></div>
              <div className="phone-screen">
                <div className="mockup-header">
                  <div className="mockup-restaurant-info">
                    <span className="mockup-live-indicator">LIVE PREVIEW</span>
                    <h3>Serengeti View Bistro</h3>
                    <p className="table-badge"><i className="fa-solid fa-location-dot"></i> Table #04 • Poolside Deck</p>
                  </div>
                  <div className="mockup-qr-stamp">
                    <i className="fa-solid fa-qrcode"></i>
                  </div>
                </div>

                {/* Filter Pills */}
                <div className="mockup-categories">
                  <button
                    className={`mock-cat-btn ${selectedCategory === 'all' ? 'active' : ''}`}
                    onClick={() => setSelectedCategory('all')}
                  >
                    All Items
                  </button>
                  <button
                    className={`mock-cat-btn ${selectedCategory === 'mains' ? 'active' : ''}`}
                    onClick={() => setSelectedCategory('mains')}
                  >
                    Mains
                  </button>
                  <button
                    className={`mock-cat-btn ${selectedCategory === 'starters' ? 'active' : ''}`}
                    onClick={() => setSelectedCategory('starters')}
                  >
                    Starters
                  </button>
                  <button
                    className={`mock-cat-btn ${selectedCategory === 'drinks' ? 'active' : ''}`}
                    onClick={() => setSelectedCategory('drinks')}
                  >
                    Drinks
                  </button>
                </div>

                {/* Menu Items List */}
                <div className="mockup-items-list">
                  {filteredDemo.map(item => (
                    <div key={item.id} className="mockup-item-card">
                      <div className="item-icon-box">{item.icon}</div>
                      <div className="item-info">
                        <div className="item-title-row">
                          <span className="item-name">{item.name}</span>
                          <span className="item-price">{item.price}</span>
                        </div>
                        <span className={`item-tag ${item.tagColor}`}>{item.tag}</span>
                        <p className="item-desc">{item.desc}</p>
                      </div>
                    </div>
                  ))}
                </div>

                {/* Active Order Footer Pill */}
                <div className="mockup-order-pill">
                  <div className="pill-status">
                    <span className="cooking-spinner">🍳</span>
                    <div>
                      <strong>Order #SM-1048</strong>
                      <span className="status-text">Chef is preparing meal</span>
                    </div>
                  </div>
                  <Link to="/customer-menu?table=1" className="pill-btn">
                    Scan Table 1
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Metrics Banner */}
      <section className="stats-banner" id="roi">
        <div className="landing-container stats-grid">
          <div className="stat-card">
            <span className="stat-number">0 sec</span>
            <span className="stat-label">Wait time to view menu</span>
            <p className="stat-sub">Immediate scan and order on guest arrival</p>
          </div>
          <div className="stat-card">
            <span className="stat-number">+22%</span>
            <span className="stat-label">Higher average ticket</span>
            <p className="stat-sub">Driven by high-res photos & smart add-ons</p>
          </div>
          <div className="stat-card">
            <span className="stat-number">100%</span>
            <span className="stat-label">Paper reprint costs eliminated</span>
            <p className="stat-sub">Modify dishes and prices in real time</p>
          </div>
          <div className="stat-card">
            <span className="stat-number">4.9 ★</span>
            <span className="stat-label">Guest feedback satisfaction</span>
            <p className="stat-sub">Internal feedback loop before Google Reviews</p>
          </div>
        </div>
      </section>

      {/* Core Features Pillars */}
      <section className="features-section" id="features">
        <div className="landing-container">
          <div className="section-head text-center">
            <span className="section-subtitle">ENTERPRISE RESTAURANT CAPABILITIES</span>
            <h2 className="section-title">Built for Modern Hospitality Excellence</h2>
            <p className="section-desc">
              From busy city center cafes to luxury safari lodges, SmartMenu gives you end-to-end control over your dining floor.
            </p>
          </div>

          <div className="features-grid">
            <div className="feature-box">
              <div className="feature-icon icon-emerald">
                <i className="fa-solid fa-qrcode"></i>
              </div>
              <h3>Dynamic QR Codes for Every Table</h3>
              <p>
                Generate unique branded QR codes for tables, bar counters, and VIP private rooms. Download vector graphics ready for table stands.
              </p>
              <ul className="feature-checklist">
                <li><i className="fa-solid fa-check"></i> Permanent QR codes (never expire)</li>
                <li><i className="fa-solid fa-check"></i> Room & location grouping</li>
                <li><i className="fa-solid fa-check"></i> High-resolution printable exports</li>
              </ul>
            </div>

            <div className="feature-box">
              <div className="feature-icon icon-amber">
                <i className="fa-solid fa-fire-burner"></i>
              </div>
              <h3>Real-Time Kitchen Display (KDS)</h3>
              <p>
                Eliminate lost paper tickets and waiter miscommunications. Orders placed at tables appear instantly on the kitchen dashboard with timestamps.
              </p>
              <ul className="feature-checklist">
                <li><i className="fa-solid fa-check"></i> Instant audio-visual alerts for new tickets</li>
                <li><i className="fa-solid fa-check"></i> Status flow: Pending → Confirmed → Served</li>
                <li><i className="fa-solid fa-check"></i> Live customer order tracker</li>
              </ul>
            </div>

            <div className="feature-box">
              <div className="feature-icon icon-blue">
                <i className="fa-solid fa-calendar-check"></i>
              </div>
              <h3>Daily Specials & 86 Item Control</h3>
              <p>
                Run out of red snapper or avocados? Mark items as unavailable in one click. They disappear immediately from guest menus to avoid disappointment.
              </p>
              <ul className="feature-checklist">
                <li><i className="fa-solid fa-check"></i> Real-time stock toggle</li>
                <li><i className="fa-solid fa-check"></i> Daily specials spotlighting</li>
                <li><i className="fa-solid fa-check"></i> Multi-category filtering</li>
              </ul>
            </div>

            <div className="feature-box">
              <div className="feature-icon icon-purple">
                <i className="fa-solid fa-star-half-stroke"></i>
              </div>
              <h3>Private In-House Feedback Shield</h3>
              <p>
                Capture guest reviews right when the bill is settled. Address unhappy guests privately before they post a negative review on TripAdvisor or Google.
              </p>
              <ul className="feature-checklist">
                <li><i className="fa-solid fa-check"></i> Instant 1 to 5 star rating prompt</li>
                <li><i className="fa-solid fa-check"></i> Private comments direct to managers</li>
                <li><i className="fa-solid fa-check"></i> Monthly satisfaction score reports</li>
              </ul>
            </div>

            <div className="feature-box">
              <div className="feature-icon icon-teal">
                <i className="fa-solid fa-language"></i>
              </div>
              <h3>Bilingual English & Swahili</h3>
              <p>
                Serve both international safari travelers and local diners seamlessly. Toggle between English and Swahili with a single tap.
              </p>
              <ul className="feature-checklist">
                <li><i className="fa-solid fa-check"></i> Full English & Kiswahili interface</li>
                <li><i className="fa-solid fa-check"></i> Localized currency formatting (TZS / USD)</li>
                <li><i className="fa-solid fa-check"></i> Auto-detection of guest device language</li>
              </ul>
            </div>

            <div className="feature-box">
              <div className="feature-icon icon-indigo">
                <i className="fa-solid fa-chart-line"></i>
              </div>
              <h3>Actionable Revenue Analytics</h3>
              <p>
                Gain deep insights into your menu's profitability. Know which dishes sell out fastest, peak order times, and top spending tables.
              </p>
              <ul className="feature-checklist">
                <li><i className="fa-solid fa-check"></i> Top-grossing dishes breakdown</li>
                <li><i className="fa-solid fa-check"></i> Table turnover velocity reports</li>
                <li><i className="fa-solid fa-check"></i> Exportable Excel & PDF sales logs</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      {/* 3-Step Walkthrough */}
      <section className="steps-section" id="how-it-works">
        <div className="landing-container">
          <div className="section-head text-center">
            <span className="section-subtitle">HOW IT WORKS</span>
            <h2 className="section-title">Ready in 3 Simple Steps</h2>
            <p className="section-desc">No expensive hardware or complex software installations required.</p>
          </div>

          <div className="steps-grid">
            <div className="step-card">
              <div className="step-number">01</div>
              <div className="step-icon">
                <i className="fa-solid fa-table-cells"></i>
              </div>
              <h3>Setup Menu & Print QRs</h3>
              <p>
                Add your delicious dishes, photos, and prices in your dashboard. Download ready-to-print QR stands for each dining table.
              </p>
            </div>

            <div className="step-card">
              <div className="step-number">02</div>
              <div className="step-icon">
                <i className="fa-solid fa-camera"></i>
              </div>
              <h3>Guests Scan & Order</h3>
              <p>
                Diners scan the table QR code with their mobile phone. They browse high-res photos, select options, and tap "Place Order".
              </p>
            </div>

            <div className="step-card">
              <div className="step-number">03</div>
              <div className="step-icon">
                <i className="fa-solid fa-bell-concierge"></i>
              </div>
              <h3>Kitchen Prepares & Delivers</h3>
              <p>
                Orders ring directly on your kitchen screen with the table number. Staff prepares and serves while tracking progress seamlessly.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Pricing Section */}
      <section className="pricing-section" id="pricing">
        <div className="landing-container">
          <div className="section-head text-center">
            <span className="section-subtitle">SIMPLE, TRANSPARENT PRICING</span>
            <h2 className="section-title">Plans Scaled for Every Restaurant</h2>
            <p className="section-desc">Start free today and upgrade as your dining room expands.</p>
          </div>

          <div className="pricing-grid">
            {/* Starter Plan */}
            <div className="pricing-card">
              <div className="pricing-tier">Starter</div>
              <div className="pricing-cost">
                <span className="currency">$</span>
                <span className="amount">0</span>
                <span className="period">/ month</span>
              </div>
              <p className="pricing-desc">Perfect for small cafes, juice bars, and pop-up eateries.</p>
              <ul className="pricing-features">
                <li><i className="fa-solid fa-check"></i> Up to 5 Tables / QR Codes</li>
                <li><i className="fa-solid fa-check"></i> Digital Visual Menu</li>
                <li><i className="fa-solid fa-check"></i> Self-Service Customer Orders</li>
                <li><i className="fa-solid fa-check"></i> English & Swahili support</li>
                <li><i className="fa-solid fa-check"></i> Standard community support</li>
              </ul>
              <Link to="/register" className="btn-plan-outline">
                Get Started Free
              </Link>
            </div>

            {/* Pro Plan */}
            <div className="pricing-card featured">
              <div className="featured-badge">MOST POPULAR</div>
              <div className="pricing-tier">Pro Restaurant</div>
              <div className="pricing-cost">
                <span className="currency">$</span>
                <span className="amount">19</span>
                <span className="period">/ month</span>
              </div>
              <p className="pricing-desc">For high-volume dining rooms, bistros, and hotels.</p>
              <ul className="pricing-features">
                <li><i className="fa-solid fa-check"></i> <strong>Unlimited Tables & Rooms</strong></li>
                <li><i className="fa-solid fa-check"></i> <strong>Live Kitchen Display System (KDS)</strong></li>
                <li><i className="fa-solid fa-check"></i> Real-time Order Tracking for Guests</li>
                <li><i className="fa-solid fa-check"></i> Daily Menu & 86 Item Control</li>
                <li><i className="fa-solid fa-check"></i> In-House Guest Feedback Shield</li>
                <li><i className="fa-solid fa-check"></i> Sales & Revenue Analytics</li>
                <li><i className="fa-solid fa-check"></i> Priority WhatsApp Support</li>
              </ul>
              <Link to="/register" className="btn-primary-gradient full-width">
                Start 14-Day Pro Trial
              </Link>
            </div>

            {/* Enterprise Plan */}
            <div className="pricing-card">
              <div className="pricing-tier">Enterprise</div>
              <div className="pricing-cost">
                <span className="currency">Custom</span>
              </div>
              <p className="pricing-desc">For multi-branch chains, hotel groups, and franchise brands.</p>
              <ul className="pricing-features">
                <li><i className="fa-solid fa-check"></i> Multi-location centralized dashboard</li>
                <li><i className="fa-solid fa-check"></i> Custom branded subdomain</li>
                <li><i className="fa-solid fa-check"></i> POS & payment gateway integration</li>
                <li><i className="fa-solid fa-check"></i> Dedicated account manager</li>
                <li><i className="fa-solid fa-check"></i> On-site staff training & onboarding</li>
              </ul>
              <a href="https://www.techwareafrica.tech" target="_blank" rel="noopener noreferrer" className="btn-plan-outline">
                Contact Enterprise Team
              </a>
            </div>
          </div>
        </div>
      </section>

      {/* FAQ Section */}
      <section className="faq-section" id="faq">
        <div className="landing-container max-w-3xl">
          <div className="section-head text-center">
            <span className="section-subtitle">HAVE QUESTIONS?</span>
            <h2 className="section-title">Frequently Asked Questions</h2>
          </div>

          <div className="faq-accordion">
            {faqs.map((faq, index) => (
              <div
                key={index}
                className={`faq-item ${activeFaq === index ? 'active' : ''}`}
                onClick={() => toggleFaq(index)}
              >
                <div className="faq-question">
                  <span>{faq.q}</span>
                  <i className={`fa-solid ${activeFaq === index ? 'fa-minus' : 'fa-plus'}`}></i>
                </div>
                {activeFaq === index && (
                  <div className="faq-answer">
                    <p>{faq.a}</p>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Final Call to Action */}
      <section className="final-cta-section">
        <div className="landing-container cta-box">
          <h2>Ready to Transform Your Dining Experience?</h2>
          <p>
            Join modern restaurants across East Africa. Setup in 5 minutes with zero upfront equipment.
          </p>
          <div className="cta-actions">
            <Link to="/register" className="btn-primary-gradient cta-large">
              Register Your Restaurant Free <i className="fa-solid fa-arrow-right"></i>
            </Link>
            <Link to="/customer-menu?table=1" className="btn-ghost-white cta-large">
              Try Interactive Demo
            </Link>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="landing-footer">
        <div className="landing-container footer-grid">
          <div className="footer-brand">
            <div className="logo-badge-small">
              <i className="fa-solid fa-qrcode"></i>
            </div>
            <h3>SmartMenu QR</h3>
            <p>
              Cloud-native digital restaurant menu and contactless ordering system designed and engineered by TechWare Africa.
            </p>
            <div className="footer-copyright">
              © {new Date().getFullYear()} TechWare Africa. All rights reserved.
            </div>
          </div>

          <div className="footer-col">
            <h4>Product</h4>
            <ul>
              <li><a href="#features">Features</a></li>
              <li><Link to="/customer-menu?table=1">Live Customer Menu</Link></li>
              <li><Link to="/order-tracking?order=1">Order Tracking</Link></li>
              <li><a href="#pricing">Pricing Plans</a></li>
              <li><a href="https://api-smartmenu.techwareafrica.tech/swagger-ui.html" target="_blank" rel="noopener noreferrer">Swagger API Docs</a></li>
            </ul>
          </div>

          <div className="footer-col">
            <h4>Solutions</h4>
            <ul>
              <li><a href="#features">Fine Dining & Bistros</a></li>
              <li><a href="#features">Beachfront Resorts & Bars</a></li>
              <li><a href="#features">Boutique Hotel Room Service</a></li>
              <li><a href="#features">Food Courts & Cafes</a></li>
            </ul>
          </div>

          <div className="footer-col">
            <h4>TechWare Africa</h4>
            <ul>
              <li><a href="https://www.techwareafrica.tech" target="_blank" rel="noopener noreferrer">Company Overview</a></li>
              <li><a href="https://www.techwareafrica.tech/products/smartmenu-qr" target="_blank" rel="noopener noreferrer">Product Profile</a></li>
              <li><Link to="/login">Restaurant Admin Portal</Link></li>
              <li><Link to="/register">Create Free Account</Link></li>
            </ul>
          </div>
        </div>
      </footer>
    </div>
  );
}
