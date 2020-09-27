#!/usr/bin/env bash

WILDFLYVER=19.1.0.Final

add-apt-repository ppa:openjdk-r/ppa --yes
apt-get update
apt-get install openjdk-11-jdk --yes

groupadd -r wildfly
useradd -r -g wildfly -d /opt/wildfly -s /sbin/nologin wildfly

wget https://download.jboss.org/wildfly/$WILDFLYVER/wildfly-$WILDFLYVER.tar.gz -P /tmp
tar xf /tmp/wildfly-$WILDFLYVER.tar.gz -C /opt/

ln -s /opt/wildfly-$WILDFLYVER /opt/wildfly
chown -RH wildfly: /opt/wildfly
mkdir -p /etc/wildfly
cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf /etc/wildfly/
cp /opt/wildfly/docs/contrib/scripts/systemd/launch.sh /opt/wildfly/bin/
sh -c 'chmod +x /opt/wildfly/bin/*.sh'
cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.service /etc/systemd/system/

systemctl daemon-reload
systemctl start wildfly

systemctl enable wildfly
