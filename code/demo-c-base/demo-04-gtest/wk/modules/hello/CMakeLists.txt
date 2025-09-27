set(LIB_SRC
  ${CMAKE_CURRENT_SOURCE_DIR}/hello.cpp
)

add_library(hellolib SHARED ${LIB_SRC})
