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
package com.innovatrics.mrz.records;

import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.MrzRange;
import com.innovatrics.mrz.MrzRecord;
import com.innovatrics.mrz.types.MrzDocumentCode;
import com.innovatrics.mrz.types.MrzFormat;

/**
 * MRV type-B format: A two lines long, 36 characters per line format
 *
 * @author Jeremy Le Berre
 */
public class MrvB extends MrzRecord {

    /**
     * Creates a new MRV-A record.
     */
    public MrvB() {
        super(MrzFormat.MRV_VISA_B);
        code1 = 'V';
        code2 = '<';
        code = MrzDocumentCode.TypeV;
    }

    /**
     * Optional data at the discretion of the issuing State
     */
    public String optional;

    @Override
    public void fromMrz(String mrz) {
        super.fromMrz(mrz);
        final MrzParser parser = new MrzParser(mrz);
        setName(parser.parseName(new MrzRange(5, 36, 0)));
        documentNumber = parser.parseString(new MrzRange(0, 9, 1));
        validDocumentNumber = parser.checkDigit(9, 1, new MrzRange(0, 9, 1), "passport number");
        nationality = parser.parseString(new MrzRange(10, 13, 1));
        dateOfBirth = parser.parseDate(new MrzRange(13, 19, 1));
        validDateOfBirth =
                parser.checkDigit(19, 1, new MrzRange(13, 19, 1), "date of birth")
                        && dateOfBirth.isDateValid();
        sex = parser.parseSex(20, 1);
        expirationDate = parser.parseDate(new MrzRange(21, 27, 1));
        validExpirationDate =
                parser.checkDigit(27, 1, new MrzRange(21, 27, 1), "expiration date")
                        && expirationDate.isDateValid();
        optional = parser.parseString(new MrzRange(28, 36, 1));
        // TODO validComposite missing? (full MRZ line)
    }

    @Override
    public String toString() {
        return "MRV-B{" + super.toString() + ", optional=" + optional + '}';
    }

    @Override
    public String toMrz() {
        String sb =
                "V<"
                        + MrzParser.toMrz(issuingCountry, 3)
                        + MrzParser.nameToMrz(surname, givenNames, 31)
                        + '\n'
                        +
                        // second line
                        MrzParser.toMrz(documentNumber, 9)
                        + MrzParser.computeCheckDigitChar(MrzParser.toMrz(documentNumber, 9))
                        + MrzParser.toMrz(nationality, 3)
                        + dateOfBirth.toMrz()
                        + MrzParser.computeCheckDigitChar(dateOfBirth.toMrz())
                        + sex.mrz
                        + expirationDate.toMrz()
                        + MrzParser.computeCheckDigitChar(expirationDate.toMrz())
                        + MrzParser.toMrz(optional, 8)
                        + '\n';
        return sb;
    }
}
