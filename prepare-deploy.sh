#!/bin/bash
# Script để chuẩn bị deploy lên Render

echo "Preparing for Render deployment..."

# Backup original context.xml
if [ ! -f "web/META-INF/context.xml.local" ]; then
    cp web/META-INF/context.xml web/META-INF/context.xml.local
    echo "Backed up local context.xml"
fi

# Replace context.xml with template version that uses env variables
cat > web/META-INF/context.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<Context path="/BTChuong14_sendEmail">
  
  <Resource name="jdbc/murach" 
            auth="Container"
            type="javax.sql.DataSource" 
            driverClassName="org.postgresql.Driver"
            url="jdbc:postgresql://dpg-d4nq9h15pdvs73ac3hb0-a.singapore-postgres.render.com:5432/render_db_fagx?characterEncoding=UTF-8"
            username="render_db_fagx_user" 
            password="hVrfapv3nbQ2UUTecQDAXpoxDgpr8Mef"
            maxTotal="20" 
            maxIdle="10" 
            maxWaitMillis="-1"/>

</Context>
EOF

echo "Updated context.xml for production"
echo "Ready to deploy!"
