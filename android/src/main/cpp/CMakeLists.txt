#
# Copyright 2021 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

cmake_minimum_required(VERSION 3.4.1)
project(gamepads_android)

find_package(games-controller REQUIRED CONFIG)

# Set common compiler options
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17 -Wall")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Werror")

# now build app's shared lib
add_library(gamepads_android SHARED
        main.cpp)

target_include_directories(gamepads_android PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR})

target_compile_options(gamepads_android
        PRIVATE
        -std=c++17
        -Wall
        -Wextra-semi
        -Wshadow
        -Wshadow-field
        -O0
        "$<$<CONFIG:DEBUG>:-Werror>")

# add lib dependencies
target_link_libraries(gamepads_android
        games-controller::paddleboat_static)
