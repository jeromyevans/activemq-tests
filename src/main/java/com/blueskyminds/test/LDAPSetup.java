package com.blueskyminds.test;

import javax.naming.Context;
import javax.naming.Name;
import java.util.Properties;

/**
 * Date Started: 12/03/2009
 */
public class LDAPSetup {

    public static final String ROOT_DN = "ou=adminobjects,o=activemq,dc=blueskyminds,dc=com,dc=au";

    public static final String QUEUE_CONNECTION_FACTORY = "cn=factory";
    public static final String QUEUE_NAME = "cn=LANDMINE.ADVERTISEMENT.IMPORT";

    public static Properties getEnv() {
        Properties env = new Properties();    
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://mq1/"+ROOT_DN);
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=blueskyminds,dc=com,dc=au");
        env.put(Context.SECURITY_CREDENTIALS, "rootpass");
        return env;
    }

}
