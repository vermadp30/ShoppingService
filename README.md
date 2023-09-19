# ShoppingService

This is a multi modular application based on SpringBoot and Kafka.


Part 1

A customer service application that needs to query a tracking service for the delivery status of an order and display it to the customer. The customer service application can use a request-reply pattern to send a request message with the order number to a topic that is consumed by the tracking service. The tracking service can use a request-reply pattern to receive the request message, look up the order status from an external courier service, and send a reply message with the status to another topic that is consumed by the customer service application. The customer service application can use a request-reply pattern to receive the reply message and show it to the customer.

The courier service can be Mocked for this problem statement which randomly returns message like "packet arrived at ${city_name} hub"

Part 2

The user queries happened in part 1 need to be captured in Elastic Search for analytics purpose. To achieve it, the postgres DB is to be synced to elasticsearch using kafka-connect.
