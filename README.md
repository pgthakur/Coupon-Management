### ✅ **Coupons Management API**

---

### 📌 **Project Overview**
This is a RESTful API for **Coupons Management** in an e-commerce platform. It allows you to create, retrieve, update, delete, and apply different types of discount coupons to a shopping cart. The API supports the following coupon types:
- **Cart-wise**: Applies a discount to the entire cart if the total amount exceeds a threshold.
- **Product-wise**: Applies a discount to specific products.
- **BxGy (Buy X, Get Y)**: Provides free products when purchasing a specified quantity of other products.

---

### 🔥 **Tech Stack**
- **Backend:** Java, Spring Boot  
- **Database:** H2 (in-memory)  
- **Build Tool:** Maven  
- **API Documentation:** Basic REST endpoints  

---

### 🚀 **API Endpoints**

#### 1️⃣ **Coupon CRUD Operations**
- **POST `/coupons`** → Create a new coupon  
- **GET `/coupons`** → Retrieve all coupons  
- **GET `/coupons/{id}`** → Retrieve a specific coupon by ID  
- **PUT `/coupons/{id}`** → Update a specific coupon by ID  
- **DELETE `/coupons/{id}`** → Delete a specific coupon by ID  

✅ **Sample Request for Creating a Coupon:**
```json
{
  "type": "cart-wise",
  "details": {
    "threshold": 100,
    "discount": 10
  }
}
```
✅ **Sample Response:**
```json
{
  "id": 1,
  "description": "10.0% off on cart total when above 100.0",
  "type": "cart-wise"
}
```

---

#### 2️⃣ **Coupon Application Endpoints**
- **POST `/applicable-coupons`** → Fetch all applicable coupons for a given cart and calculate the total discount that will be applied by each coupon.  
- **POST `/apply-coupon/{id}`** → Apply a specific coupon to the cart and return the updated cart with discounted prices for each item.  

✅ **Sample Request for `/applicable-coupons`:**
```json
{
  "cart": {
    "items": [
      { "product_id": 101, "quantity": 2, "price": 500 },
      { "product_id": 102, "quantity": 1, "price": 200 }
    ]
  }
}
```

✅ **Sample Response:**
```json
{
  "applicable_coupons": [
    {
      "id": 1,
      "description": "10% off on cart total above ₹1000",
      "discount_amount": 70
    },
    {
      "id": 2,
      "description": "5% off on Product 102",
      "discount_amount": 10
    }
  ]
}
```

✅ **Sample Request for `/apply-coupon/{id}`:**
```json
{
  "cart": {
    "items": [
      { "product_id": 101, "quantity": 2, "price": 500 },
      { "product_id": 102, "quantity": 1, "price": 200 }
    ]
  }
}
```

✅ **Sample Response:**
```json
{
  "cart": {
    "total_price": 1200,
    "discounted_price": 1080,
    "coupon_applied": "10% off on cart total above ₹1000"
  }
}
```

---

### ⚙️ **Implemented Cases**
1. **Cart-wise Coupon**
   - Applies a percentage discount on the total cart value if it exceeds the threshold.
   - Example: `10% off on carts above ₹100`.
2. **Product-wise Coupon**
   - Applies a percentage discount to specific products.
   - Example: `20% off on Product A`.
3. **BxGy Coupon**
   - "Buy X, Get Y" type deals with repetition limits.
   - Example: `Buy 3 of Product X, get 1 of Product Y free`.

---

### 🚫 **Unimplemented Cases & How to Implement Them**

#### 1️⃣ **Coupon Expiry**
- **Issue:** Currently, coupons do not have an expiry date.
- **How to Implement:**
   - Add `expiry_date` field to the `Coupon` entity.
   - Add validation to check if the coupon is expired before applying it.

✅ **Example in `Coupon.java`:**
```java
import java.time.LocalDate;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String type;
    private LocalDate expiryDate;  // New field

    // Getters and Setters
}
```

✅ **Validation during coupon application:**
```java
if (coupon.getExpiryDate().isBefore(LocalDate.now())) {
    throw new CouponExpiredException("Coupon is expired.");
}
```

---

#### 2️⃣ **Coupon Stacking (Combining Multiple Coupons)**
- **Issue:** The current implementation does not allow applying multiple coupons simultaneously.
- **How to Implement:**
   - Modify the `apply-coupon` logic to accept multiple coupon IDs.
   - Apply the discounts sequentially or as a cumulative discount.

✅ **Sample Request:**
```json
{
  "cart_id": 1,
  "coupon_ids": [1, 2] 
}
```

✅ **Updated Controller:**
```java
@PostMapping("/apply-coupons")
public ResponseEntity<Cart> applyMultipleCoupons(@RequestBody ApplyCouponsRequest request) {
    Cart cart = couponService.applyMultipleCoupons(request.getCartId(), request.getCouponIds());
    return ResponseEntity.ok(cart);
}
```

---

#### 3️⃣ **Coupon Usage Limit per User**
- **Issue:** The current implementation allows unlimited usage of coupons.
- **How to Implement:**
   - Add a `usage_limit` field to the `Coupon` entity.
   - Track how many times each user has applied a coupon.
   - Prevent further application once the limit is reached.

✅ **Example in `Coupon.java`:**
```java
private int usageLimit;    // Maximum times the coupon can be used
private int usedCount;      // Track how many times it has been used
```

✅ **Validation in service layer:**
```java
if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
    throw new CouponLimitExceededException("Coupon usage limit reached.");
}
```

---

### ⚠️ **Limitations**
1. **In-memory database:**  
   - The project uses H2 (in-memory) for simplicity, which means data is not persistent after the application stops.
2. **Basic validation only:**  
   - Limited error handling (invalid input and missing coupons).
3. **No authentication/authorization:**  
   - The API is currently unprotected and accessible without authentication.

---

### ✅ **Future Enhancements**
1. **Add JWT-based authentication** for secure access.
2. **Support for coupon expiry dates**.
3. **Improve validation** with stricter rules.
4. Use **MySQL or PostgreSQL** for persistent data storage.
5. Add **unit tests** for better code coverage.
6. Implement **logging and error handling** for better debugging.
7. Introduce **rate limiting** to prevent abuse of the coupon system.

---

✅ **Author:** Prabhat Gaurav  
📧 **Contact:** prabhatgaurav@example.com  
🚀 **Version:** 1.0
