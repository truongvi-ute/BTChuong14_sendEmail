# ✅ TODO: Những việc BẠN cần làm trước khi deploy

## 🔴 BẮT BUỘC (Không làm sẽ build fail)

### 1. Thêm file JAR vào thư mục `lib/`

**Cần 2 files:**
- `lib/activation-1.1.jar`
- `lib/mail-1.4.7.jar`

**Cách tải:**

Option A - Tải thủ công:
1. Mở: https://mvnrepository.com/artifact/javax.activation/activation/1.1
2. Click "jar" để tải `activation-1.1.jar`
3. Mở: https://mvnrepository.com/artifact/javax.mail/mail/1.4.7
4. Click "jar" để tải `mail-1.4.7.jar`
5. Copy 2 file vào thư mục `lib/`

Option B - Dùng wget (nếu có):
```bash
cd lib
wget https://repo1.maven.org/maven2/javax/activation/activation/1.1/activation-1.1.jar
wget https://repo1.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar
cd ..
```

### 2. Sửa file `nbproject/project.properties`

Mở file `nbproject/project.properties`, tìm dòng 30-31:

**Thay đổi từ:**
```properties
file.reference.activation-1.1.jar=G:\\Webs\\lib\\activation-1.1.jar
file.reference.mail-1.4.7.jar=G:\\Webs\\lib\\mail-1.4.7.jar
```

**Thành:**
```properties
file.reference.activation-1.1.jar=lib/activation-1.1.jar
file.reference.mail-1.4.7.jar=lib/mail-1.4.7.jar
```

### 3. Test build local

```bash
ant clean
ant dist
```

Nếu thành công, sẽ có file: `dist/BTChuong14_sendEmail.war`

---

## 🟡 KHUYẾN NGHỊ (Nên làm)

### 4. Kiểm tra Gmail App Password còn hoạt động

1. Truy cập: https://myaccount.google.com/apppasswords
2. Kiểm tra App Password: `khgn pvmm ldma bnbu` còn valid không
3. Nếu không, tạo mới và update trong code

### 5. Verify database credentials

Kiểm tra database trên Render còn hoạt động:
- URL: `dpg-d4nq9h15pdvs73ac3hb0-a.singapore-postgres.render.com`
- Username: `render_db_fagx_user`
- Password: `hVrfapv3nbQ2UUTecQDAXpoxDgpr8Mef`

---

## 🟢 TÙY CHỌN (Có thể bỏ qua)

### 6. Test local trước khi deploy

1. Start Tomcat local
2. Deploy WAR file
3. Test form đăng ký
4. Kiểm tra email có gửi được không

---

## 📤 Sau khi hoàn thành TODO:

### Push lên GitHub:

```bash
git add .
git commit -m "Ready for Render deployment"
git push
```

### Deploy trên Render:

1. Vào https://render.com
2. New + → Web Service
3. Connect GitHub repo
4. Set environment variables:
   - `DB_USERNAME` = `render_db_fagx_user`
   - `DB_PASSWORD` = `hVrfapv3nbQ2UUTecQDAXpoxDgpr8Mef`
   - `GMAIL_EMAIL` = `nguyendoantruongvi11@gmail.com`
   - `GMAIL_APP_PASSWORD` = `khgn pvmm ldma bnbu`
5. Click "Create Web Service"

---

## ❓ Cần giúp đỡ?

- Build fail? → Kiểm tra lại bước 1 và 2
- Email không gửi được? → Xem `SWITCH_TO_SENDGRID.md`
- Database lỗi? → Kiểm tra credentials
- Các vấn đề khác? → Xem `RENDER_CHECKLIST.md`
