adb -s emulator-5554 uninstall com.faa1192.weatherforecast
adb -s emulator-5556 uninstall com.faa1192.weatherforecast
adb -s emulator-5558 uninstall com.faa1192.weatherforecast
adb -s emulator-5560 uninstall com.faa1192.weatherforecast
adb -s emulator-5562 uninstall com.faa1192.weatherforecast
adb -s emulator-5564 uninstall com.faa1192.weatherforecast
adb -s emulator-5566 uninstall com.faa1192.weatherforecast
adb -s emulator-5568 uninstall com.faa1192.weatherforecast
adb -s emulator-5570 uninstall com.faa1192.weatherforecast
adb -s emulator-5572 uninstall com.faa1192.weatherforecast

adb -s emulator-5554 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5556 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5558 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5560 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5562 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5564 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5566 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5568 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5570 install .\app\build\outputs\apk\app-debug.apk
adb -s emulator-5572 install .\app\build\outputs\apk\app-debug.apk

adb -s emulator-5554 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5556 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5558 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5560 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5562 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5564 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5566 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5568 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5570 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
adb -s emulator-5572 shell am start -n com.faa1192.weatherforecast/.Preferred.PrefCitiesActivity
