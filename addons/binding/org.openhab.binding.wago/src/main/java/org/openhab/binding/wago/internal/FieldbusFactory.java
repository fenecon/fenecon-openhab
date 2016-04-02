package org.openhab.binding.wago.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.openhab.binding.wago.internal.channels.FbChannel;
import org.openhab.binding.wago.internal.channels.FbInputCoilChannel;
import org.openhab.binding.wago.internal.channels.FbInputCoilChannelGroup;
import org.openhab.binding.wago.internal.channels.FbOutputCoilChannel;
import org.openhab.binding.wago.internal.channels.FbOutputCoilChannelGroup;
import org.openhab.binding.wago.internal.modules.FbModule;
import org.openhab.binding.wago.internal.modules.FbModuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import net.wimpi.modbus.net.TCPMasterConnection;

public class FieldbusFactory {
    private static Logger logger = LoggerFactory.getLogger(FieldbusFactory.class);
    private static int ftpPort = 21;

    public Fieldbus getFieldbus(InetAddress ip, int port, String username, String password)
            throws IOException, ParserConfigurationException, SAXException, TransformerException {
        List<FbInputCoilChannel> fbInputCoilChannels = new LinkedList<FbInputCoilChannel>();
        List<FbOutputCoilChannel> fbOutputCoilChannels = new LinkedList<FbOutputCoilChannel>();
        FbModuleFactory moduleFactory = new FbModuleFactory();

        URL url = new URL("ftp://" + username + ":" + password + "@" + ip.getHostAddress() + ":" + ftpPort
                + "/etc/EA-config.xml;type=i");
        URLConnection urlc = url.openConnection();
        InputStream is = urlc.getInputStream();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();

        Node wagoNode = doc.getElementsByTagName("WAGO").item(0);
        if (wagoNode != null) {
            Node moduleNode = wagoNode.getFirstChild();
            while (moduleNode != null) {
                if (moduleNode.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap moduleAttrs = moduleNode.getAttributes();
                    String article = moduleAttrs.getNamedItem("ARTIKELNR").getNodeValue();
                    // int channelcount = Integer.parseInt(moduleAttrs.getNamedItem("CHANNELCOUNT").getNodeValue());
                    // String map = moduleAttrs.getNamedItem("MAP").getNodeValue();
                    String moduletype = moduleAttrs.getNamedItem("MODULETYPE").getNodeValue();

                    Node channelNode = moduleNode.getFirstChild();
                    List<String> channeltypes = new LinkedList<String>();
                    while (channelNode != null) {
                        if (channelNode.getNodeType() == Node.ELEMENT_NODE) {
                            NamedNodeMap channelAttrs = channelNode.getAttributes();
                            // String channelname = channelAttrs.getNamedItem("CHANNELNAME").getNodeValue();
                            // String channeltype = channelAttrs.getNamedItem("CHANNELTYPE").getNodeValue();
                            channeltypes.add(channelAttrs.getNamedItem("CHANNELTYPE").getNodeValue());
                        }
                        channelNode = channelNode.getNextSibling();
                    }
                    FbModule fbModule = moduleFactory.getFbModule(article, moduletype, channeltypes);
                    if (fbModule != null) {
                        logger.info("Found module: " + fbModule);
                        for (FbChannel channel : fbModule.getChannels()) {
                            if (channel instanceof FbInputCoilChannel) {
                                fbInputCoilChannels.add((FbInputCoilChannel) channel);
                            } else if (channel instanceof FbOutputCoilChannel) {
                                fbOutputCoilChannels.add((FbOutputCoilChannel) channel);
                            }
                        }
                    }
                }
                moduleNode = moduleNode.getNextSibling();
            }
        }

        FbInputCoilChannelGroup fbInputCoilChannelGroup = new FbInputCoilChannelGroup(
                fbInputCoilChannels.toArray(new FbInputCoilChannel[fbInputCoilChannels.size()]));
        FbOutputCoilChannelGroup fbOutputCoilChannelGroup = new FbOutputCoilChannelGroup(
                fbOutputCoilChannels.toArray(new FbOutputCoilChannel[fbOutputCoilChannels.size()]));
        TCPMasterConnection tcpConnection = new TCPMasterConnection(ip);
        tcpConnection.setPort(port);
        return new Fieldbus(tcpConnection, fbInputCoilChannelGroup, fbOutputCoilChannelGroup);

    }
}
