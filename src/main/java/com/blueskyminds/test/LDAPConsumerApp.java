package com.blueskyminds.test;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;

public class LDAPConsumerApp 
{
    private static final Log LOG = LogFactory.getLog(LDAPConsumerApp.class);

    public static void main( String[] args )
    {
        Context jndiContext = null;
        QueueConnectionFactory connectionFactory = null;
        Queue destination = null;
        try {
            jndiContext = new InitialContext(LDAPSetup.getEnv());
        } catch (NamingException e) {
            LOG.error("Coould not create JNDI context", e);
        }

        if (jndiContext != null) {
            try {
                connectionFactory = (QueueConnectionFactory) jndiContext.lookup(LDAPSetup.QUEUE_CONNECTION_FACTORY);

                destination = (Queue) jndiContext.lookup(LDAPSetup.QUEUE_NAME);
            } catch (NamingException e) {

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            if ((connectionFactory != null) && (destination != null)) {
                Connection connection = null;
                try {
                    LOG.info("Creating connection to "+destination);
                    // todo: if the queue server is down this is where an error is thrown
                    // eg. UnknownHosException
                    connection = connectionFactory.createConnection();

                    //connection.setExceptionListener(this);
                    connection.start();

                    LOG.info("Creating session...");
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                    MessageConsumer consumer = session.createConsumer(destination);

                    LOG.info("Creating message listener...");
                    consumer.setMessageListener(new MyConsumer());
                    LOG.info("now what?");
                } catch (JMSException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }
    }
}