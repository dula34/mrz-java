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
package com.innovatrics.mrz.types;

/**
 * MRZ sex.
 *
 * @author Martin Vysny
 */
public enum MrzSex {

    /**
     * The male sex.
     */
    Male('M'),
    /**
     * The female sex.
     */
    Female('F'),
    /**
     * The unspecified sex.
     */
    Unspecified('X');

    /**
     * The MRZ character.
     */
    public final char mrz;

    MrzSex(char mrz) {
        this.mrz = mrz;
    }

    /**
     * Converts MRZ character to {@link MrzSex}.
     *
     * @param sex the MRZ character
     * @return the corresponding {@link MrzSex}
     * @throws RuntimeException if the MRZ character is invalid
     */
    public static MrzSex fromMrz(char sex) {
        return switch (sex) {
            case 'M' -> Male;
            case 'F' -> Female;
            case '<', 'X' -> Unspecified;
            default -> throw new RuntimeException("Invalid MRZ sex character: " + sex);
        };
    }
}
