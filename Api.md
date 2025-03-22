# API Documentation

## AssetController

### GET `/api/assets/{assetId}`
- **Description**: Get asset by ID.
- **Path Variable**: `assetId` (Long).
- **Response**: `Asset` object.

### GET `/api/assets/coin/{coinId}/user`
- **Description**: Get asset by user ID and coin ID.
- **Path Variable**: `coinId` (String).
- **Request Header**: `Authorization` (String).
- **Response**: `Asset` object.

### GET `/api/assets`
- **Description**: Get assets for user.
- **Request Header**: `Authorization` (String).
- **Response**: List of `Asset` objects.

## AuthController

### POST `/auth/signup`
- **Description**: Register a new user.
- **Request Body**: `User` object.
- **Response**: `AuthResponse` object with JWT token.

### POST `/auth/signin`
- **Description**: Login a user.
- **Request Body**: `User` object.
- **Response**: `AuthResponse` object with JWT token.

### POST `/auth/two-factor/otp/{otp}`
- **Description**: Verify two-factor authentication OTP.
- **Path Variable**: `otp` (String).
- **Request Param**: `id` (String).
- **Response**: `AuthResponse` object.

## CoinController

### GET `/coins`
- **Description**: Get coin list.
- **Request Param**: `page` (int).
- **Response**: List of `Coin` objects.

### GET `/coins/{coinId}/chart`
- **Description**: Get market chart.
- **Path Variable**: `coinId` (String).
- **Request Param**: `days` (int).
- **Response**: `JsonNode` object.

### GET `/coins/search`
- **Description**: Search coin.
- **Request Param**: `q` (String).
- **Response**: `JsonNode` object.

### GET `/coins/top50`
- **Description**: Get top 50 coins by market cap rank.
- **Response**: `JsonNode` object.

### GET `/coins/trading`
- **Description**: Get trading coins.
- **Response**: `JsonNode` object.

### GET `/coins/details/{coinId}`
- **Description**: Get coin details.
- **Path Variable**: `coinId` (String).
- **Response**: `JsonNode` object.

## HomeController

### GET `/`
- **Description**: Home endpoint.
- **Response**: `String` message.

### GET `/secure`
- **Description**: Secure endpoint.
- **Response**: `String` message.

## OrderController

### POST `/api/orders/pay`
- **Description**: Pay order payment.
- **Request Header**: `Authorization` (String).
- **Request Body**: `CreateOrderRequest` object.
- **Response**: `Order` object.

### GET `/api/orders/{order_id}`
- **Description**: Get order by ID.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `order_id` (Long).
- **Response**: `Order` object.

### GET `/api/orders`
- **Description**: Get all orders for user.
- **Request Header**: `Authorization` (String).
- **Request Param**: `orderType` (OrderType), `assetsSymbol` (String).
- **Response**: List of `Order` objects.

## PaymentController

### POST `/api/payment/{paymentMethod}/amount/{amount}`
- **Description**: Handle payment.
- **Path Variable**: `paymentMethod` (PaymentMethod), `amount` (Long).
- **Request Header**: `Authorization` (String).
- **Response**: `PaymentResponse` object.

## PaymentDetailsController

### POST `/api/payment-details`
- **Description**: Add payment details.
- **Request Header**: `Authorization` (String).
- **Request Body**: `PaymentDetails` object.
- **Response**: `PaymentDetails` object.

### GET `/api/payment-details`
- **Description**: Get user's payment details.
- **Request Header**: `Authorization` (String).
- **Response**: `PaymentDetails` object.

## UserController

### GET `/api/users/profile`
- **Description**: Get user profile.
- **Request Header**: `Authorization` (String).
- **Response**: `User` object.

### POST `/api/users/verification/{verificationType}/send-otp`
- **Description**: Send verification OTP.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `verificationType` (VerificationType).
- **Response**: `String` message.

### PATCH `/api/users/enable-two-factor/verify-otp/{otp}`
- **Description**: Enable two-factor authentication.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `otp` (String).
- **Response**: `User` object.

### POST `/auth/users/reset-password/send-otp`
- **Description**: Send forgot password OTP.
- **Request Body**: `ForgotPasswordRequest` object.
- **Response**: `AuthResponse` object.

### PATCH `/auth/users/reset-password/verify-otp`
- **Description**: Reset password.
- **Request Param**: `id` (String).
- **Request Body**: `ResetPasswordRequest` object.
- **Response**: `ApiResponse` object.

## WalletController

### GET `/api/wallet`
- **Description**: Get user wallet.
- **Request Header**: `Authorization` (String).
- **Response**: `Wallet` object.

### PUT `/api/wallet/deposit/amount/{amount}`
- **Description**: Deposit money.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `amount` (Long).
- **Response**: `PaymentResponse` object.

### PUT `/api/wallet/deposit`
- **Description**: Add money to wallet.
- **Request Header**: `Authorization` (String).
- **Request Param**: `order_id` (Long), `payment_id` (String).
- **Response**: `Wallet` object.

### PUT `/api/wallet/{walletId}/transfer`
- **Description**: Wallet to wallet transfer.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `walletId` (Long).
- **Request Body**: `WalletTransaction` object.
- **Response**: `Wallet` object.

### PUT `/api/wallet/order/{orderId}/pay`
- **Description**: Pay order payment.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `orderId` (Long).
- **Response**: `Wallet` object.

### PUT `/api/wallet/balance`
- **Description**: Add balance to wallet.
- **Request Header**: `Authorization` (String).
- **Request Param**: `order_Id` (Long), `payment_id` (String).
- **Response**: `Wallet` object.

## WatchlistController

### GET `/api/watchlist/user`
- **Description**: Get user watchlist.
- **Request Header**: `Authorization` (String).
- **Response**: `Watchlist` object.

### POST `/api/watchlist/create`
- **Description**: Create watchlist.
- **Request Header**: `Authorization` (String).
- **Response**: `Watchlist` object.

### GET `/api/watchlist/{watchlistId}`
- **Description**: Get watchlist by ID.
- **Path Variable**: `watchlistId` (Long).
- **Response**: `Watchlist` object.

### PATCH `/api/watchlist/add/coin/{coinId}`
- **Description**: Add item to watchlist.
- **Request Header**: `Authorization` (String).
- **Path Variable**: `coinId` (String).
- **Response**: `Coin` object.

## WithdrawlController

### POST `/api/Withdrawl/{amount}`
- **Description**: Request withdrawal.
- **Path Variable**: `amount` (Long).
- **Request Header**: `Authorization` (String).
- **Response**: `Withdrawl` object.

### PATCH `/api/admin/Withdrawl/{id}/proceed/{accept}`
- **Description**: Proceed withdrawal.
- **Path Variable**: `id` (Long), `accept` (boolean).
- **Request Header**: `Authorization` (String).
- **Response**: `Withdrawl` object.

### GET `/api/Withdrawl`
- **Description**: Get withdrawal history.
- **Request Header**: `Authorization` (String).
- **Response**: List of `Withdrawl` objects.

### GET `/api/admin/Withdrawl`
- **Description**: Get all withdrawal requests.
- **Request Header**: `Authorization` (String).
- **Response**: List of `Withdrawl` objects.<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>