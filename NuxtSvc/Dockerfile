# ビルド環境
FROM node:lts-alpine as build-stage

COPY . /src/

WORKDIR /src/

RUN npm install && REPOSITORY_URL=${REPOSITORY_URL} npm run generate 

# 本番環境
FROM nginx as production-stage

# HTMLファイル
COPY  --from=build-stage ./src/dist/ /usr/share/nginx/html/

# NGINX設定ファイル
COPY ./default.conf.template /etc/nginx/conf.d/default.conf.template

# 起動
CMD ["/bin/bash", "-c", "envsubst '$$REPOSITORY_URL' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
