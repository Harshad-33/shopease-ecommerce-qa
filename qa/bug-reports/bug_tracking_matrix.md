# Bug Tracking Matrix

**Document ID:** BTM-SHOPEASE-2026-V1  
**Project:** ShopEase E-Commerce  
**QA Lead / Reviewer:** QA Engineering Intern  

---

## Defect Summary by Severity & Priority

| Severity | High Priority | Medium Priority | Low Priority | Total |
| :--- | :---: | :---: | :---: | :---: |
| **Critical** | 0 | 0 | 0 | **0** |
| **High** | 0 | 0 | 0 | **0** |
| **Medium** | 1 | 3 | 1 | **5** |
| **Low** | 0 | 2 | 3 | **5** |
| **Total** | **1** | **5** | **4** | **10** |

---

## Comprehensive Defect Tracking Log

| Bug ID | Title | Module | Severity | Priority | Reported By | Status | Target Sprint |
| :--- | :--- | :--- | :---: | :---: | :--- | :---: | :---: |
| **BUG-001** | Phone input accepts non-numeric characters without regex | Registration | Medium | Medium | QA Intern | Open | Sprint 2 |
| **BUG-002** | Navbar search bar does not trim whitespace | Search | Low | Low | QA Intern | Open | Sprint 2 |
| **BUG-003** | PIN Code field allows alphabetic characters | Checkout | Medium | Medium | QA Intern | Open | Sprint 2 |
| **BUG-004** | Missing warning when Cart quantity reaches max stock | Cart | Low | Medium | QA Intern | Open | Sprint 2 |
| **BUG-005** | Demo Card Expiry accepts past expired dates | Checkout | Medium | Low | QA Intern | Open | Sprint 3 |
| **BUG-006** | Text overflow on ultra-narrow mobile viewports (<360px) | Orders | Low | Low | QA Intern | Open | Sprint 3 |
| **BUG-007** | Admin modal price input accepts zero/negative values | Admin | Medium | High | QA Intern | Open | Sprint 2 |
| **BUG-008** | Order cancellation lacks confirmation modal | Admin Orders | Low | Medium | QA Intern | Open | Sprint 2 |
| **BUG-009** | Clearing search input does not auto-refresh catalog | Search | Low | Low | QA Intern | Open | Sprint 3 |
| **BUG-010** | Browser back button returns to checkout with empty cart | Checkout | Medium | Medium | QA Intern | Open | Sprint 2 |

---

## Root Cause Analysis (RCA) Insights for Technical Interview

During an interview, you can explain how these defects were categorized:
1. **Client-Side vs Server-Side Validation Gap (BUG-001, BUG-003):**  
   *Why it happens:* The backend has robust entity constraints, but the frontend lacks matching HTML5 / Angular reactive form validators. In a production team, defensive programming requires validating on both layers.
2. **UX / State Synchronization (BUG-004, BUG-009):**  
   *Why it happens:* Buttons or inputs change internal state without communicating transparently to the user via alerts, tooltips, or reactive two-way bindings.
3. **Admin Safety Controls (BUG-008):**  
   *Why it happens:* Direct two-way binding on status dropdown triggers instant HTTP PUT without an intervening modal dialog, creating vulnerability to human error in operations.
