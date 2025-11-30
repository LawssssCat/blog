# logstash

```bash
wget https://artifacts.elastic.co/downloads/logstash/logstash-9.2.1-linux-x86_64.tar.gz
```

```bash
bin/logstash -e 'input { stdin { } } output { stdout {} }'
kkk
{
         "event" => {
        "original" => "kkk"
    },
    "@timestamp" => 2025-11-23T04:20:54.481028474Z,
       "message" => "kkk",
          "host" => {
        "hostname" => "demo-se-elk"
    },
      "@version" => "1"
}
```

```bash
bin/logstash -f ../test-pipeline.conf --config.test_and_exit
bin/logstash -f ../test-pipeline.conf --config.reload.automatic
```
