package com.blueskyminds.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;

/**
 * Try sending a message
 *
 * Date Started: 06/03/2009
 */
public class ProducerApp {

    private static final Log LOG = LogFactory.getLog(ProducerApp.class);

    public static void main(String[] args) {
        Context jndiContext = null;
        QueueConnectionFactory connectionFactory = null;
        
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            LOG.error("Coould not create JNDI context", e);
        }

        if (jndiContext != null) {
            Destination destination = null;
            try {
                connectionFactory = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");

                destination = (Queue) jndiContext.lookup("MyQueue");
            } catch (NamingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            if ((connectionFactory != null) && (destination != null)) {
                Connection connection = null;
                try {
                    LOG.info("Creating connection to "+destination);
                    connection = connectionFactory.createConnection();

                    //connection.setExceptionListener(this);
                    connection.start();

                    LOG.info("Creating session...");
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                    LOG.info("Creating persistent producer...");
                    MessageProducer producer = session.createProducer(destination);
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);

                    LOG.info("Send message");
                    TextMessage message = session.createTextMessage("This is a sample message");
                    producer.send(message);
//                               if (transacted) {
//                                   session.commit();
//                               }


                    LOG.info("done?");
                } catch (JMSException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        }
    }    

}
