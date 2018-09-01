// Copyright (c) 2011-2015 ScalaMock Contributors (https://github.com/paulbutcher/ScalaMock/graphs/contributors)
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.scalamock_example.test
package orderspec

import org.scalamock.specs2.MockContext

import org.specs2.mutable.Specification

import com.scalamock_example.app.{Order, Warehouse}

class OrderSpec extends  Specification {
    "An order" should {
        "remove inventory when in stock" in new MockContext {
            val warehouseMock = mock[Warehouse]
            inSequence {
                (warehouseMock.hasInventory _).expects("Machu", 50).returning(true).once
                (warehouseMock.remove _).expects("Machu", 50).once
            }

            val order = new Order("Machu", 50)
            order.fill(warehouseMock)
            order.isFilled must beTrue
        }

        "remove nothing when out of stock" in new MockContext {
            val warehouseMock = mock[Warehouse]
            (warehouseMock.hasInventory _).expects(*, *).returns(false).once
            val order =  new Order("Machu", 50)
            order.fill(warehouseMock)
            order.isFilled must beFalse
        }
    }
}