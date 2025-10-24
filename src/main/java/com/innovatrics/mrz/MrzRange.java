/**
 * Java parser for the MRZ records, as specified by the ICAO organization.
 * Copyright (C) 2011 Innovatrics s.r.o.
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.innovatrics.mrz;

import java.io.Serializable;

/**
 * Represents a text selection range.
 *
 * @param column   0-based index of first character in the range.
 * @param columnTo 0-based index of a character after last character in the range.
 * @param row      0-based row.
 * @author Martin Vysny
 */
public record MrzRange(int column, int columnTo, int row) implements Serializable {

    /**
     * Creates new MRZ range object.
     *
     * @param column   0-based index of first character in the range.
     * @param columnTo 0-based index of a character after last character in the range.
     * @param row      0-based row.
     */
    public MrzRange {
        if (column > columnTo) {
            throw new IllegalArgumentException(
                    "Parameter column: invalid value "
                            + column
                            + ": must be less than "
                            + columnTo);
        }
    }

    @Override
    public String toString() {
        return column + "-" + columnTo + "," + row;
    }

    /**
     * Returns length of this range.
     *
     * @return number of characters, which this range covers.
     */
    public int length() {
        return columnTo - column;
    }
}
