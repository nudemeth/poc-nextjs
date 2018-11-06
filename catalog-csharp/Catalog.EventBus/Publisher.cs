using RabbitMQ.Client;
using System;
using System.Text;

namespace Catalog.EventBus
{
    public class Publisher
    {
        public void Send()
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            
            using (var connection = factory.CreateConnection())
            using (var channel = connection.CreateModel())
            {
                channel.ExchangeDeclare(exchange: "direct_logs",
                                    type: "direct");

                var severity = "info";
                var message = "Hello World!";
                var body = Encoding.UTF8.GetBytes(message);
                channel.BasicPublish(exchange: "direct_logs",
                                    routingKey: severity,
                                    basicProperties: null,
                                    body: body);
                Console.WriteLine(" [x] Sent '{0}':'{1}'", severity, message);
            }
        }
    }
}