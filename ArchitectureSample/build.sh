#!/usr/bin/env bash

cd ~/
export ANDROID_HOME="~/android-sdk"
export PATH="$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools:$PATH"
cd architecture_sample/ArchitectureSample/
chmod a+x ./gradlew
./gradlew publish