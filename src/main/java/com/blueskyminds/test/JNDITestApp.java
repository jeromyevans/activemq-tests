package com.blueskyminds.test;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import java.util.Properties;

/**
 * Test setup JNDI to openldap
 * <p/>
 * Date Started: 12/03/2009
 */
public class JNDITestApp {

    private static final Log LOG = LogFactory.getLog(JNDITestApp.class);

    public static void main(String[] args) {
        Properties env = new Properties();

        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://mq1/");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,cn=config");
        env.put(Context.SECURITY_CREDENTIALS, "rootpass");

        try {
            DirContext ctx = new InitialDirContext( env );
        } catch (NamingException e) {
            LOG.error(e);
        }
    }
}
