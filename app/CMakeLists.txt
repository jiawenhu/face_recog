# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# ##################### OpenCV 环境 ############################
#设置OpenCV-android-sdk路径
set( OpenCV_DIR D:/OpenCV4Android/OpenCV-android-sdk/sdk/native/jni )

find_package(OpenCV REQUIRED )
if(OpenCV_FOUND)
    include_directories(${OpenCV_INCLUDE_DIRS})
    message(STATUS "OpenCV library status:")
    message(STATUS "    version: ${OpenCV_VERSION}")
    message(STATUS "    libraries: ${OpenCV_LIBS}")
    message(STATUS "    include path: ${OpenCV_INCLUDE_DIRS}")
else(OpenCV_FOUND)
    message(FATAL_ERROR "OpenCV library not found")
endif(OpenCV_FOUND)

# ###################网上解决preemptible问题的建议################
set( CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,--exclude-libs,libippicv.a -Wl,--exclude-libs,libippiw.a")

# ###################### 项目原生模块 ###########################
# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.
# 创建并命名库，将其设置为静态或共享，
# 并提供与其源代码的相对路径。
# 您可以定义多个库，CMake会为您构建它。
# 使用APK时会自动打包共享库。

add_library( # 本地c++源文件（库）的名字 Sets the name of the library.
             com_test_opencvdemo3_JniActivity

             # 把上面那些库，设置为“共享” Sets the library as a shared library.
             SHARED

             # 库的路径，也就是相应的本地源文件的路径 Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             src/main/cpp/com_test_opencvdemo3_JniActivity.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # 路径变量 Sets the name of the path variable.
              log-lib

              # 把自己希望CMake去定位的NDK库名写在这
              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.
# 指定CMake应该链接到目标库的库。可以链接多个库，例如在构建脚本中定义的库、预构建的第三方库或系统库。

target_link_libraries( # Specifies the target library.
                       com_test_opencvdemo3_JniActivity
                       ${OpenCV_LIBS}
                       log
                       jnigraphics

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
