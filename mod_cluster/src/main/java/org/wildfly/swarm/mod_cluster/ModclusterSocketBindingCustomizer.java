package org.wildfly.swarm.mod_cluster;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.wildfly.swarm.container.runtime.DefaultSocketBindingGroupProducer;
import org.wildfly.swarm.spi.api.Customizer;
import org.wildfly.swarm.spi.api.Pre;
import org.wildfly.swarm.spi.api.SocketBinding;
import org.wildfly.swarm.spi.api.SocketBindingGroup;
import org.wildfly.swarm.spi.api.annotations.ConfigurationValue;

/**
 * @author Bob McWhirter
 */
@Singleton
@Pre
public class ModclusterSocketBindingCustomizer implements Customizer {

    @Inject
    @Named(DefaultSocketBindingGroupProducer.STANDARD_SOCKETS)
    private SocketBindingGroup group;

    @Inject
    @ConfigurationValue(ModclusterProperties.MULTICAST_ADDRESS)
    private String address;

    @Inject
    @ConfigurationValue(ModclusterProperties.MULTICAST_PORT)
    private Integer port;

    @Override
    public void customize() {

        if ( this.address == null ) {
            this.address = "224.01.105";
        }

        if ( this.port == null ) {
            this.port = 23364;
        }

        this.group.socketBinding(
                new SocketBinding("modcluster")
                        .port(0)
                        .multicastAddress(this.address)
                        .multicastPort(this.port));

    }
}
