#!/usr/bin/env bash

cd ~/
wget --quiet --output-document=android-sdk.zip https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip
unzip -o -qq android-sdk.zip -d android-sdk
export ANDROID_HOME="~/android-sdk"
export PATH="$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools:$PATH"
# Download packages.
yes | sdkmanager "platform-tools"
yes | sdkmanager "platforms;android-$COMPILE_SDK_VERSION"
yes | sdkmanager "build-tools;$BUILD_TOOLS_VERSION"
yes | sdkmanager "extras;android;m2repository"
yes | sdkmanager "extras;google;m2repository"
yes | sdkmanager "extras;google;instantapps"
yes | sdkmanager --licenses
