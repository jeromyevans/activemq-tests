package com.blueskyminds.test;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.jms.*;
import java.util.Properties;

/**
 * Looks up a queue, creates a producer and sends a message
 *
 * Date Started: 12/03/2009
 */
public class LDAPProducerApp {

    private static final Log LOG = LogFactory.getLog(LDAPProducerApp.class);

    public static void main(String[] args) {

        DirContext jndiContext = null;
        QueueConnectionFactory connectionFactory = null;

        try {
            jndiContext = new InitialDirContext(LDAPSetup.getEnv());
        } catch (NamingException e) {
            LOG.error("Coould not create JNDI context", e);
        }

        if (jndiContext != null) {
            Destination destination = null;
            try {
                connectionFactory = (ActiveMQConnectionFactory) jndiContext.lookup(LDAPSetup.QUEUE_CONNECTION_FACTORY);

                destination = (Queue) jndiContext.lookup(LDAPSetup.QUEUE_NAME);
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
