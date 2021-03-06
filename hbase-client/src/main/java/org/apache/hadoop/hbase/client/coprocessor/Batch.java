/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.client.coprocessor;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

import java.io.IOException;


/**
 * A collection of interfaces and utilities used for interacting with custom RPC
 * interfaces exposed by Coprocessors.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public abstract class Batch {
  /**
   * Defines a unit of work to be executed.
   *
   * <p>
   * When used with
   * {@link org.apache.hadoop.hbase.client.HTable#coprocessorService(Class, byte[], byte[], org.apache.hadoop.hbase.client.coprocessor.Batch.Call)}
   * the implementations {@link Batch.Call#call(Object)} method will be invoked
   * with a proxy to the
   * {@link org.apache.hadoop.hbase.coprocessor.CoprocessorService}
   * sub-type instance.
   * </p>
   * @see org.apache.hadoop.hbase.client.coprocessor
   * @see org.apache.hadoop.hbase.client.HTable#coprocessorService(byte[])
   * @see org.apache.hadoop.hbase.client.HTable#coprocessorService(Class, byte[], byte[], org.apache.hadoop.hbase.client.coprocessor.Batch.Call)
   * @param <T> the instance type to be passed to
   * {@link Batch.Call#call(Object)}
   * @param <R> the return type from {@link Batch.Call#call(Object)}
   */
  public static interface Call<T,R> {
    public R call(T instance) throws IOException;
  }

  /**
   * Defines a generic callback to be triggered for each {@link Batch.Call#call(Object)}
   * result.
   *
   * <p>
   * When used with
   * {@link org.apache.hadoop.hbase.client.HTable#coprocessorService(Class, byte[], byte[], org.apache.hadoop.hbase.client.coprocessor.Batch.Call)}
   * the implementation's {@link Batch.Callback#update(byte[], byte[], Object)}
   * method will be called with the {@link Batch.Call#call(Object)} return value
   * from each region in the selected range.
   * </p>
   * @param <R> the return type from the associated {@link Batch.Call#call(Object)}
   * @see org.apache.hadoop.hbase.client.HTable#coprocessorService(Class, byte[], byte[], org.apache.hadoop.hbase.client.coprocessor.Batch.Call)
   */
  public static interface Callback<R> {
    public void update(byte[] region, byte[] row, R result);
  }
}