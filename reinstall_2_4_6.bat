adb -s emulator-5556 uninstall com.faa1192.weatherforecast
adb -s emulator-5568 uninstall com.faa1192.weatherforecast
adb -s emulator-5574 uninstall com.faa1192.weatherforecast

adb -s emulator-5556 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5568 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5574 install .\app\build\outputs\apk\app-debug.apk

adb -s emulator-5556 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5568 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5574 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity