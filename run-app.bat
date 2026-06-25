@echo off
setlocal

cd /d "%~dp0"

set "AVD_NAME=Medium_Phone_API_36.1"
set "APP_PACKAGE=app.mihon.dev"
set "SDK_DIR=%LOCALAPPDATA%\Android\Sdk"
set "ADB=%SDK_DIR%\platform-tools\adb.exe"
set "EMULATOR=%SDK_DIR%\emulator\emulator.exe"
set "APK=%~dp0app\build\outputs\apk\debug\app-universal-debug.apk"
set "ANDROID_STUDIO_JBR=C:\Program Files\Android\Android Studio\jbr"

if not exist "%ADB%" (
    echo Android Debug Bridge not found:
    echo %ADB%
    exit /b 1
)

if not exist "%EMULATOR%" (
    echo Android emulator not found:
    echo %EMULATOR%
    exit /b 1
)

if exist "%ANDROID_STUDIO_JBR%\bin\java.exe" (
    set "JAVA_HOME=%ANDROID_STUDIO_JBR%"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)

echo Building debug APK...
call "%~dp0gradlew.bat" :app:assembleDebug
if errorlevel 1 exit /b %errorlevel%

if not exist "%APK%" (
    echo APK not found:
    echo %APK%
    exit /b 1
)

set "DEVICE="
for /f "skip=1 tokens=1,2" %%A in ('"%ADB%" devices') do (
    if "%%B"=="device" set "DEVICE=%%A"
)

if not defined DEVICE (
    echo Starting Android emulator: %AVD_NAME%
    start "Android Emulator" "%EMULATOR%" -avd "%AVD_NAME%"
    echo Waiting for emulator...
    "%ADB%" wait-for-device
) else (
    echo Using connected device: %DEVICE%
)

set "BOOTED="
echo Waiting for Android boot to complete...
:wait_boot
for /f "usebackq tokens=*" %%A in (`"%ADB%" shell getprop sys.boot_completed 2^>nul`) do set "BOOTED=%%A"
if not "%BOOTED%"=="1" (
    timeout /t 3 /nobreak >nul
    goto wait_boot
)

echo Installing APK...
"%ADB%" install -r "%APK%"
if errorlevel 1 exit /b %errorlevel%

echo Launching %APP_PACKAGE%...
"%ADB%" shell monkey -p %APP_PACKAGE% -c android.intent.category.LAUNCHER 1
if errorlevel 1 exit /b %errorlevel%

echo Done.
