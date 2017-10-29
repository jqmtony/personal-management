/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Vladislav Zablotsky
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. 
 */
package cn.xt.base.core.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Multi dimensional array handler.
 * <p>
 * PostgreSQL require that such arrays must be as matrix - all rows must have same amount of elements.
 */
@MappedTypes(byte[][].class)
public class ArrayByte2dTypeHandler extends BaseTypeHandler<byte[][]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, byte[][] parameter, JdbcType jdbcType)
            throws SQLException {
        //数据库按照Object类型存储参数
        ps.setObject(i, parameter);
    }

    @Override
    public byte[][] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return getByte2dFromRs(rs, columnName);
    }

    @Override
    public byte[][] getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return getByte2dFromRs(rs, columnIndex);
    }

    @Override
    public byte[][] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        byte[] bytes = cs.getBytes(columnIndex);
        return getByte2d(bytes);
    }
    public byte[][] getByte2dFromRs(ResultSet rs, String columnName) {
        byte[] bytes = new byte[0];
        try {
            bytes = rs.getBytes(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getByte2d(bytes);
    }

    public byte[][] getByte2dFromRs(ResultSet rs, int columnIndex) {
        byte[] bytes = new byte[0];
        try {
            bytes = rs.getBytes(columnIndex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getByte2d(bytes);
    }

    public byte[][] getByte2d(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                byte[][] object = (byte[][]) objectInputStream.readObject();
                return object;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
