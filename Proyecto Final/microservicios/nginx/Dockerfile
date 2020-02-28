FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf
# Start Nginx when the container has provisioned.
CMD ["nginx", "-g", "daemon off;"]