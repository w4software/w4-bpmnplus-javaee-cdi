package eu.w4.contrib.bpmnplus.cdi;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import eu.w4.common.exception.CheckedException;
import eu.w4.engine.client.service.ActivityService;
import eu.w4.engine.client.service.AttributeDefinitionService;
import eu.w4.engine.client.service.AuthenticationService;
import eu.w4.engine.client.service.ConversationService;
import eu.w4.engine.client.service.DefinitionsService;
import eu.w4.engine.client.service.EngineService;
import eu.w4.engine.client.service.EventService;
import eu.w4.engine.client.service.GatewayService;
import eu.w4.engine.client.service.GroupService;
import eu.w4.engine.client.service.LanguageService;
import eu.w4.engine.client.service.ObjectFactory;
import eu.w4.engine.client.service.PrivilegeService;
import eu.w4.engine.client.service.ProcessService;
import eu.w4.engine.client.service.PrototypingService;
import eu.w4.engine.client.service.PublicKeyService;
import eu.w4.engine.client.service.ServerService;
import eu.w4.engine.client.service.TenantService;
import eu.w4.engine.client.service.TransactionService;
import eu.w4.engine.client.service.TypeDefinitionService;
import eu.w4.engine.client.service.UserService;

public class EngineServicesFactory
{

  @Produces
  public AuthenticationService getAuthenticationService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getAuthenticationService();
  }

  @Produces
  public TransactionService getTransactionService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getTransactionService();
  }

  @Produces
  public TenantService getTenantService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getTenantService();
  }

  @Produces
  public UserService getUserService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getUserService();
  }

  @Produces
  public GroupService getGroupService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getGroupService();
  }

  @Produces
  public ActivityService getActivityService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getActivityService();
  }

  @Produces
  public ProcessService getProcessService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getProcessService();
  }

  @Produces
  public DefinitionsService getDefinitionsService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getDefinitionsService();
  }

  @Produces
  public PrivilegeService getPrivilegeService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getPrivilegeService();
  }

  @Produces
  public PrototypingService getPrototypingService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getPrototypingService();
  }

  @Produces
  public ConversationService getConversationService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getConversationService();
  }

  @Produces
  public AttributeDefinitionService getAttributeDefinitionService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getAttributeDefinitionService();
  }

  @Produces
  public TypeDefinitionService getTypeDefinitionService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getTypeDefinitionService();
  }

  @Produces
  public EventService getEventService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getEventService();
  }

  @Produces
  public LanguageService getLanguageService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getLanguageService();
  }

  @Produces
  public GatewayService getGatewayService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getGatewayService();
  }

  @Produces
  public PublicKeyService getPublicKeyService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getPublicKeyService();
  }

  @Produces
  public ServerService getServerService(EngineService engineService) throws RemoteException, CheckedException
  {
    return engineService.getServerService();
  }

  @ApplicationScoped
  @Produces
  public ObjectFactory getObjectFactory(EngineService engineService) throws RemoteException, CheckedException
  {
    ObjectFactory factory = engineService.getObjectFactory();
    return (ObjectFactory) Proxy.newProxyInstance(factory.getClass().getClassLoader(), new Class[] { ObjectFactory.class }, new ResultCachingProxyHandler<ObjectFactory>(factory));
  }
}
