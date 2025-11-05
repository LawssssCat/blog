#include "json/value.h"
#include "json/writer.h"
#include <iostream>

#include <gtest/gtest.h>

#include <json/version.h>
#include <json/json.h>
#include <string>

// #include <json/

TEST(jsoncpp_hello, show_world) {
  std::cout << "hello world ~ " << JSONCPP_VERSION_STRING << std::endl;

  Json::Value jsonRoot; // 定义根节点

  // value
  jsonRoot["intValue"] = -99;
  jsonRoot["uintValue"] = 100;
  std::cout << "int:" << jsonRoot["intValue"].asInt() << std::endl;
  std::cout << "uint:" << jsonRoot["uintValue"].asUInt() << std::endl;

  // array
  for(int i=0; i<4; ++i) {
    if (i % 2 == 0) {
      jsonRoot["arrValue"][i] = std::to_string(i);
    } else {
      jsonRoot["arrValue"][i] = i;
    }
  }
  std::cout << "jsonRoot[arrValue][0]:" << jsonRoot["arrValue"][0] << std::endl;
  std::cout << "jsonRoot[arrValue].size():" << jsonRoot["arrValue"].size() << std::endl;

  // object
  Json::Value jsonItem; // 子对象
  jsonItem["itemKey01"] = "one";
  jsonRoot["RootItem01"] = jsonItem;

  // format
  std::cout << "jsonRoot(format)=" << jsonRoot.toStyledString() << std::endl;
  // std::cout << "jsonRoot(format):" << jsonRoot.asCString() << std::endl; // only string can as string, otherwise exception
  // unformat
  Json::StreamWriterBuilder writer;
  writer["indentation"] = ""; // Set indentation to an empty string for unstyled output
  std::string unstyledJson = Json::writeString(writer, jsonRoot);
  std::cout << "jsonRoot(unformat)=" << unstyledJson << std::endl;
}
