# 🚀 Hướng dẫn Deploy lên Render

## 📋 Tổng quan

Dự án này đã được cấu hình sẵn để deploy lên Render với:
- ✅ Dockerfile cho containerization
- ✅ Environment variables cho bảo mật
- ✅ SMTP configuration tối ưu cho cloud
- ✅ Database connection pool

## 🔧 Bước 1: Chuẩn bị thư viện JAR

Bạn cần copy 2 file JAR vào thư mục `lib/`:
- `activation-1.1.jar`
- `mail-1.4.7.jar`

**Nếu chưa có**, tải từ:
- https://mvnrepository.com/artifact/javax.activation/activation/1.1
- https://mvnrepository.com/artifact/javax.mail/mail/1.4.7

## 📝 Bước 2: Cập nhật project.properties

Mở file `nbproject/project.properties` và thay đổi dòng 30-31:

```properties
# TỪ:
file.reference.activation-1.1.jar=G:\\Webs\\lib\\activation-1.1.jar
file.reference.mail-1.4.7.jar=G:\\Webs\\lib\\mail-1.4.7.jar

# THÀNH:
file.reference.activation-1.1.jar=lib/activation-1.1.jar
file.reference.mail-1.4.7.jar=lib/mail-1.4.7.jar
```

## 🧪 Bước 3: Test build local

```bash
# Windows CMD:
ant clean & ant dist

# Hoặc PowerShell:
ant clean; ant dist
```

File WAR sẽ được tạo trong `dist/BTChuong14_sendEmail.war`

## 📤 Bước 4: Push code lên GitHub

```bash
git init
git add .
git commit -m "Ready for Render deployment"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git
git push -u origin main
```

## ☁️ Bước 5: Deploy trên Render

### 5.1. Tạo Web Service

1. Đăng nhập https://render.com
2. Click **"New +"** → **"Web Service"**
3. Connect GitHub repository
4. Render tự động detect `render.yaml`

### 5.2. Cấu hình Environment Variables

Trong phần **Environment**, thêm:

| Key | Value |
|-----|-------|
| `DB_USERNAME` | `render_db_fagx_user` |
| `DB_PASSWORD` | `hVrfapv3nbQ2UUTecQDAXpoxDgpr8Mef` |
| `GMAIL_EMAIL` | `nguyendoantruongvi11@gmail.com` |
| `GMAIL_APP_PASSWORD` | `khgn pvmm ldma bnbu` |

### 5.3. Deploy

Click **"Create Web Service"** và đợi ~5-10 phút

## ✅ Bước 6: Kiểm tra

1. Truy cập URL: `https://your-app-name.onrender.com`
2. Test form đăng ký email
3. Kiểm tra email inbox
4. Xem logs nếu có lỗi

## 📊 Monitoring

### Xem Logs
```
Render Dashboard → Your Service → Logs
```

### Kiểm tra email gửi thành công
- Tìm dòng: `DEBUG SMTP: message successfully delivered`
- Nếu có lỗi, sẽ hiện: `ERROR: Unable to send email`

## ⚠️ Vấn đề thường gặp

### 1. Email không gửi được

**Triệu chứng:** Timeout hoặc Authentication failed

**Nguyên nhân:** Render có thể block SMTP port 587

**Giải pháp:** Xem file `SWITCH_TO_SENDGRID.md` để chuyển sang SendGrid

### 2. Database connection failed

**Kiểm tra:**
- Environment variables đã set đúng chưa?
- Database trên Render còn hoạt động không?

### 3. Build failed

**Kiểm tra:**
- File JAR đã có trong thư mục `lib/` chưa?
- `project.properties` đã update đúng chưa?

## 📚 Tài liệu bổ sung

- `RENDER_CHECKLIST.md` - Checklist chi tiết về khả năng gửi email
- `SWITCH_TO_SENDGRID.md` - Hướng dẫn chuyển sang SendGrid
- `.env.example` - Template cho environment variables

## 🎯 Lưu ý quan trọng

### Free Tier Limitations:
- ⏰ Service sleep sau 15 phút không hoạt động
- 🐌 Wake up mất ~30 giây
- 💾 Database free tier: 90 ngày
- 📧 Gmail SMTP có thể bị giới hạn

### Khuyến nghị:
- Dùng SendGrid cho production (reliable hơn)
- Upgrade paid plan nếu cần uptime 24/7
- Monitor logs thường xuyên

## 🆘 Cần trợ giúp?

1. Kiểm tra `RENDER_CHECKLIST.md`
2. Xem logs trên Render Dashboard
3. Test local trước khi deploy
4. Đảm bảo Gmail App Password còn valid
