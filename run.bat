@echo off
REM Compile semua file Java
echo [1] Meng-compile file Java...

javac --release 17 -d bin -cp "lib/mysql-connector-j-9.3.0.jar" -sourcepath . ^
user\*.java connection\*.java kategori\*.java alat\*.java penyewaan\*.java ^
detail_penyewaan\*.java tagihan_penyewaan\*.java pengembalian\*.java ^
stack\*.java Main.java

IF %ERRORLEVEL% NEQ 0 (
    echo [!] Kompilasi gagal. Cek error di atas.
    pause
    exit /b
)

echo [2] Menjalankan program...

REM Jalankan program Java
java -cp "bin;lib/mysql-connector-j-9.3.0.jar" Main

pause