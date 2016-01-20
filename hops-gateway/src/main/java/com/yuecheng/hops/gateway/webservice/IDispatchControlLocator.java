/**
 * IDispatchControlLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yuecheng.hops.gateway.webservice;

public class IDispatchControlLocator extends org.apache.axis.client.Service implements com.yuecheng.hops.gateway.webservice.IDispatchControl {

    public IDispatchControlLocator() {
    }


    public IDispatchControlLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IDispatchControlLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IDispatchControlHttpPort
    private java.lang.String IDispatchControlHttpPort_address = "http://bill.bestpay.com.cn:37007/services/businessService";

    public java.lang.String getIDispatchControlHttpPortAddress() {
        return IDispatchControlHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IDispatchControlHttpPortWSDDServiceName = "IDispatchControlHttpPort";

    public java.lang.String getIDispatchControlHttpPortWSDDServiceName() {
        return IDispatchControlHttpPortWSDDServiceName;
    }

    public void setIDispatchControlHttpPortWSDDServiceName(java.lang.String name) {
        IDispatchControlHttpPortWSDDServiceName = name;
    }

    public com.yuecheng.hops.gateway.webservice.IDispatchControlPortType getIDispatchControlHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IDispatchControlHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIDispatchControlHttpPort(endpoint);
    }

    public com.yuecheng.hops.gateway.webservice.IDispatchControlPortType getIDispatchControlHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.yuecheng.hops.gateway.webservice.IDispatchControlHttpBindingStub _stub = new com.yuecheng.hops.gateway.webservice.IDispatchControlHttpBindingStub(portAddress, this);
            _stub.setPortName(getIDispatchControlHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIDispatchControlHttpPortEndpointAddress(java.lang.String address) {
        IDispatchControlHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IDispatchControlPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                IDispatchControlHttpBindingStub _stub = new IDispatchControlHttpBindingStub(new java.net.URL(IDispatchControlHttpPort_address), this);
                _stub.setPortName(getIDispatchControlHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IDispatchControlHttpPort".equals(inputPortName)) {
            return getIDispatchControlHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://control.ppcore.haobai.huateng.com", "IDispatchControl");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://control.ppcore.haobai.huateng.com", "IDispatchControlHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IDispatchControlHttpPort".equals(portName)) {
            setIDispatchControlHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
