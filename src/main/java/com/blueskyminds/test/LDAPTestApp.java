package com.blueskyminds.test;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.jms.Queue;
import java.util.Properties;

/**
 * Create a Queue (Destination) in the LDAP directory using JNDI
 *
 * Date Started: 12/03/2009
 */
public class LDAPTestApp {

    private static final Log LOG = LogFactory.getLog(LDAPTestApp.class);

    public static void main(String[] args) {
        Properties env = LDAPSetup.getEnv();

        try {
            DirContext ctx = new InitialDirContext(env);

//            Integer i = 1;
//            ctx.bind("cn=randomInt", i);
//
//            i = (Integer) ctx.lookup("cn=randomInt");
            //ctx.bind("cn=factory2", new ActiveMQConnectionFactory("tcp://mq1:61616"));

            ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) ctx.lookup(LDAPSetup.QUEUE_CONNECTION_FACTORY);

            Queue queue = (Queue) ActiveMQQueue.createDestination("LANDMINE.ADVERTISEMENT.IMPORT", ActiveMQQueue.QUEUE_TYPE);
                        
            ctx.bind("cn=LANDMINE.ADVERTISEMENT.IMPORT", queue);

        } catch (NamingException e) {
            LOG.error(e);
        }
    }
}
