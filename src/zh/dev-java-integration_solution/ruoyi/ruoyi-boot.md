---
title: RuoYi 启动笔记
date: 2024-09-19
order: 99
---

准备好 mysql 和 redis 即可

```yaml
apiVersion: apps/v1
kind: Pod
metadata:
  name: ruoyi-pod
spec:
  containers:
    - name: mysql
      image: docker.io/library/mysql:5.7.35
      # imagePullPolicy: IfNotPresent
      # command:
      # args:
      ports:
        - containerPort: 3306
          hostPort: 23306
          # port: 30080             # 服务访问端口，集群内部访问的端口
          # targetPort: 3306          # pod控制器中定义的端口（应用访问的端口）
          # nodePort: 23306           # NodePort，外部客户端访问的端口
      volumeMounts:
        - name: mysql-data
          mountPath: /var/lib/mysql
        - name: mysql-logs
          mountPath: /var/lib/logs
        # - name: mysql-conf
        #   mountPath: /etc/mysql/mysql.conf.d/mysql.cnf
      env:
        - name: MYSQL_ROOT_PASSWORD
          value: 123456
        - name: MYSQL_USER
          value: azi
        - name: MYSQL_PASSWORD
          value: 123mysql
  volumes:
    - name: mysql-data
      emptyDir: {}
      # hostPath:
      #   path: ./mount/data/
    # - name: mysql-conf
    #   hostPath:
    #     path: ./mount/conf/mysql.cnf
    - name: mysql-logs
      emptyDir: {}
      # hostPath:
      #   path: ./mount/logs/
```
