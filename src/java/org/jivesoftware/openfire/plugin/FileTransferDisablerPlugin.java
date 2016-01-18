package org.jivesoftware.openfire.plugin;

import org.dom4j.Element;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Packet;

import java.io.File;

public class FileTransferDisablerPlugin implements Plugin
{
    /**
     * The Stream Initiation, SI, namespace.
     */
    static final String NAMESPACE_SI = "http://jabber.org/protocol/si";

    /**
     * Namespace for the file transfer profile of Stream Initiation.
     */
    static final String NAMESPACE_SI_FILETRANSFER = "http://jabber.org/protocol/si/profile/file-transfer";

    private FileTransferRejector rejector = new FileTransferRejector();

    public void initializePlugin( PluginManager manager, File pluginDirectory )
    {
        //register our interceptor when the plugin is loaded
        InterceptorManager.getInstance().addInterceptor( rejector );
    }

    public void destroyPlugin()
    {
        //remove our interceptor so the plugin can be unloaded cleanly
        InterceptorManager.getInstance().removeInterceptor( rejector );
    }

    private class FileTransferRejector implements PacketInterceptor
    {
        public void interceptPacket( Packet packet, Session session, boolean incoming, boolean processed ) throws PacketRejectedException
        {
            // We only want packets received by the server
            if ( !processed && incoming && packet instanceof IQ )
            {
                IQ iq = (IQ) packet;
                Element childElement = iq.getChildElement();
                if ( childElement == null )
                {
                    return;
                }

                String namespace = childElement.getNamespaceURI();
                if ( NAMESPACE_SI.equals( namespace ) )
                {
                    // If this is a set, check the feature offer
                    if ( iq.getType().equals( IQ.Type.set ) )
                    {
                        //check if the packet has a file-transfer profile
                        String profile = childElement.attributeValue( "profile" );
                        if ( NAMESPACE_SI_FILETRANSFER.equals( profile ) )
                        {
                            //if the packet has a file-transfer profile, reject the packet
                            throw new PacketRejectedException();
                        }
                    }
                }
            }
        }
    }
}
