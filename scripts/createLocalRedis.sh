#!/bin/bash
docker run                          \
    --name redis-user-registration  \
    -p 6379:6379                    \
    -v /var/redis-data:/data        \
    -d                              \
    redis:6.0-rc1-alpine            \
    redis-server --requirepass minhaSenhaLocal