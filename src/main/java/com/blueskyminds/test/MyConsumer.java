package com.blueskyminds.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;

/**
 * Date Started: 06/03/2009
 */
public class MyConsumer implements MessageListener {

    private static final Log LOG = LogFactory.getLog(MyConsumer.class);

    public MyConsumer() {
        LOG.info("MyConsumer created");
    }

    public void onMessage(Message message) {
        try {
            LOG.info("Message received:"+((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
