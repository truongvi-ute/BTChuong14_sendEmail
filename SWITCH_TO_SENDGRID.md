# Hướng dẫn chuyển sang SendGrid (nếu Gmail không hoạt động)

## Khi nào cần chuyển?

Nếu sau khi deploy lên Render mà gửi email bị lỗi:
- Connection timeout
- Authentication failed
- Gmail block IP

## Các bước chuyển sang SendGrid:

### 1. Đăng ký SendGrid (Free)
- Truy cập: https://sendgrid.com
- Đăng ký tài khoản (Free tier: 100 emails/day)
- Verify email của bạn

### 2. Tạo API Key
- Vào Settings > API Keys
- Click "Create API Key"
- Chọn "Full Access"
- Copy API Key (chỉ hiện 1 lần!)

### 3. Verify Sender Email
- Vào Settings > Sender Authentication
- Click "Verify a Single Sender"
- Nhập email: nguyendoantruongvi11@gmail.com
- Check email và verify

### 4. Cập nhật code

Mở file: `src/java/controller/EmailListServlet.java`

Thay đổi dòng 56:
```java
// TỪ:
MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

// THÀNH:
MailUtilSendGrid.sendMail(to, from, subject, body, isBodyHTML);
```

### 5. Thêm Environment Variables trên Render

Vào Render Dashboard > Your Service > Environment:
```
SENDGRID_API_KEY=SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
SENDGRID_FROM_EMAIL=nguyendoantruongvi11@gmail.com
```

### 6. Rebuild và Deploy

```bash
git add .
git commit -m "Switch to SendGrid for email"
git push
```

Render sẽ tự động rebuild và deploy.

## Ưu điểm của SendGrid:

✅ Không bị block bởi Render
✅ Reliable hơn Gmail SMTP
✅ Có dashboard theo dõi emails
✅ Free 100 emails/day (đủ cho testing)
✅ Dễ scale lên paid plan

## Test

Sau khi deploy xong, test lại form đăng ký. Email sẽ được gửi qua SendGrid.
