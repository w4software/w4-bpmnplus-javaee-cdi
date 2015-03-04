package eu.w4.contrib.bpmnplus.cdi;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import eu.w4.common.exception.CheckedException;
import eu.w4.engine.client.eci.service.EciContentService;
import eu.w4.engine.client.eci.service.EciObjectFactory;
import eu.w4.engine.client.eci.service.EciUserService;
import eu.w4.engine.client.service.EngineService;

public class EciServicesFactory
{

  @Produces
  public EciContentService getContentService(final EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getEciContentService();
  }

  @Produces
  public EciUserService getUserService(final EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getEciUserService();
  }

  @ApplicationScoped
  @Produces
  public EciObjectFactory getObjectFactory(final EngineService engineService) throws RemoteException, CheckedException
  {
    final EciObjectFactory factory = engineService.getEciObjectFactory();
    return (EciObjectFactory) Proxy.newProxyInstance(factory.getClass().getClassLoader(), new Class[] { EciObjectFactory.class }, new ResultCachingProxyHandler<EciObjectFactory>(factory));
  }

}
