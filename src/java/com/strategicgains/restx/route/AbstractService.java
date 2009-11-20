/*
 * Copyright 2009, Strategic Gains, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.strategicgains.restx.route;

import com.strategicgains.restx.serialization.DeserializationException;
import com.strategicgains.restx.serialization.Deserializer;
import com.strategicgains.restx.serialization.SerializationException;
import com.strategicgains.restx.serialization.Serializer;

/**
 * @author toddf
 * @since Nov 20, 2009
 */
public abstract class AbstractService
implements Service
{
	// SECTION: INSTANCE VARIABLES

	private Resolver<Serializer> serializerResolver;
	private Resolver<Deserializer> deserializerResolver;

	
	// SECTION: ACCESSORS/MUTATORS

	/**
     * @return the serializerResolver
     */
    public Resolver<Serializer> getSerializerResolver()
    {
    	return serializerResolver;
    }

	/**
     * @param serializerResolver the serializerResolver to set
     */
    public void setSerializerResolver(Resolver<Serializer> serializerResolver)
    {
    	this.serializerResolver = serializerResolver;
    }

	/**
     * @return the deserializerResolver
     */
    public Resolver<Deserializer> getDeserializerResolver()
    {
    	return deserializerResolver;
    }

	/**
     * @param deserializerResolver the deserializerResolver to set
     */
    public void setDeserializerResolver(Resolver<Deserializer> deserializerResolver)
    {
    	this.deserializerResolver = deserializerResolver;
    }

	
	// SECTION: SERVICE

	@Override
	public Object deserialize(Request request)
	throws DeserializationException
	{
		Deserializer deserializer;

		try
        {
	        deserializer = getDeserializerResolver().resolve(request);
        }
        catch (UnsupportedRequestException e)
        {
        	throw new DeserializationException(e);
        }
        
		return deserializer.deserialize(request);
	}

	@Override
	public Response serialize(Request request, Object object)
	throws SerializationException
	{
		Serializer serializer;

		try
        {
	        serializer = getSerializerResolver().resolve(request);
        }
        catch (UnsupportedRequestException e)
        {
        	throw new SerializationException(e);
        }
        
		String result = serializer.serialize(request);
		
		//TODO: create a Response.
		return null;
	}
}
