# Coupon API

This project is a Spring Boot application that provides a RESTful API for managing and applying coupons. It supports different types of coupon logic such as cart-wise, product-wise, and buy-x-get-y.

## Features

- **Cart-wise Coupons**: Apply a discount on the total cart value when the threshold amount is reached.
- **Product-wise Coupons**: Apply a discount to specific products based on their ID.
- **Buy-X-Get-Y Coupons (BXGY)**: Buy a specified quantity of certain products and get another product free.

## API Endpoints

### 1. Create Coupons
- **`POST /coupons`**
  - **Request Body**: 
    ```json
    {
      "type": "cart-wise",
      "details": {
        "threshold": 100,
        "discount": 10
      }
    }
    ```

  - **Response**: Created coupon with ID and details.

- **`POST /coupons`**
  - **Request Body**: 
    ```json
    {
      "type": "product-wise",
      "details": {
        "product_id": 1,
        "discount": 20
      }
    }
    ```

  - **Response**: Created coupon with ID and details.

- **`POST /coupons`**
  - **Request Body**: 
    ```json
    {
      "type": "bxgy",
      "details": {
        "buy_products": [
          {"product_id": 1, "quantity": 3},
          {"product_id": 2, "quantity": 3}
        ],
        "get_products": [
          {"product_id": 3, "quantity": 1}
        ],
        "repition_limit": 2
      }
    }
    ```

  - **Response**: Created coupon with ID and details.

### 2. Get Applicable Coupons
- **`POST /applicable-coupons`**
  - **Request Body**: 
    ```json
    {
      "cart": {
        "items": [
          {"product_id": 1, "quantity": 6, "price": 50},
          {"product_id": 2, "quantity": 3, "price": 30},
          {"product_id": 3, "quantity": 2, "price": 25}
        ]
      }
    }
    ```

  - **Response**: 
    ```json
    {
      "applicable_coupons": [
        {
          "coupon_id": 1,
          "type": "cart-wise",
          "discount": 40
        },
        {
          "coupon_id": 3,
          "type": "bxgy",
          "discount": 50
        }
      ]
    }
    ```

### 3. Apply Coupon to Cart
- **`POST /apply-coupon/{id}`**
  - **Path Variable**: `{id}` - The ID of the coupon to apply.
  - **Request Body**: 
    ```json
    {
      "cart": {
        "items": [
          {"product_id": 1, "quantity": 6, "price": 50},
          {"product_id": 2, "quantity": 3, "price": 30},
          {"product_id": 3, "quantity": 2, "price": 25}
        ]
      }
    }
    ```

  - **Response**: 
    ```json
    {
      "updated_cart": {
        "items": [
          {"product_id": 1, "quantity": 6, "price": 50, "total_discount": 0},
          {"product_id": 2, "quantity": 3, "price": 30, "total_discount": 0},
          {"product_id": 3, "quantity": 4, "price": 25, "total_discount": 50}
        ],
        "total_price": 490,
        "total_discount": 50,
        "final_price": 440
      }
    }
    ```

## Running the Application

To run the application:

1. Clone the repository.
2. Build the project using Maven or Gradle.
3. Run the application class (e.g., `CouponApiApplication.main()` in your IDE or with `mvn spring-boot:run` from the command line).

## Database Seeding

The application includes a `DatabaseSeeder` that initializes the database with test data. The `CommandLineRunner` bean in the `DatabaseSeeder` class seeds the database with sample coupons for testing purposes.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Data JPA
- **Database**: PostgreSQL
- **Testing**: JUnit, Mockito

## Contributing

Feel free to contribute to this project. Pull requests are welcome. Please make sure to update tests and documentation accordingly.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
