package eu.w4.contrib.bpmnplus.cdi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ResultCachingProxyHandler<E> implements InvocationHandler
{
  public class ContextObjectInputStream extends ObjectInputStream
  {
    private ClassLoader _classloader;

    public ContextObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException
    {
        super(in);
        this._classloader = classLoader;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc)
      throws IOException, ClassNotFoundException
    {
      try
      {
        return Class.forName(desc.getName(), false, _classloader);
      }
      catch (ClassNotFoundException ex)
      {
        return super.resolveClass(desc);
      }
    }
  }

  private E _delegate;
  private Map<String, byte[]> _cache = new HashMap<String, byte[]>();

  public ResultCachingProxyHandler(E delegate)
  {
    _delegate = delegate;
  }

  private void save(String key, Object object)
    throws IOException
  {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ObjectOutputStream objectOutput = new ObjectOutputStream(output);
    objectOutput.writeObject(object);
    objectOutput.flush();
    objectOutput.close();
    _cache.put(key, output.toByteArray());
  }

  private Object newCopy(String key, ClassLoader cl)
    throws ClassNotFoundException, IOException
  {
    byte[] copy = _cache.get(key);
    ObjectInputStream stream = new ContextObjectInputStream(new ByteArrayInputStream(copy), cl);
    try
    {
      return stream.readObject();
    }
    finally
    {
      stream.close();
    }
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args)
    throws Throwable
  {
    String cacheKey = method.getName();
    if (!_cache.containsKey(cacheKey))
    {
      // $$$$ shall the containsKey also be synchronized?
      synchronized (_cache)
      {
        Object result = method.invoke(_delegate, args);
        save(cacheKey, result);
      }
    }
    return newCopy(cacheKey, proxy.getClass().getClassLoader());
  }

}
