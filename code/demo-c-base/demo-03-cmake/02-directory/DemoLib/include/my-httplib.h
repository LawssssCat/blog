#include <condition_variable>
#include <functional>

class Client {
public:
  ~Client();
  Result Get(const std::string &path, DownloadProgress progress = nullptr);
};

class Result {
  // Response
  const Response &value() const { return *res_; }
  Response &value() { return *res_; }
private:
  std::unique_ptr<Response> res_;
};

using DownloadProgress = std::function<bool(size_t current, size_t total)>;

struct Response {
  int status = -1;
};
