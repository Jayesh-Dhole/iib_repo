<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Define the output format -->
    <xsl:output method="xml" indent="yes" encoding="UTF-8" />

    <!-- Template to match the root element 'employees' -->
    <xsl:template match="/employees">
        <!-- Root element of the output XML -->
        <company>
            <!-- Loop through each 'employee' element -->
            <xsl:for-each select="employee">
                <staff>
                    <!-- Map 'name' to 'fullName' in the output -->
                    <fullName><xsl:value-of select="name" /></fullName>
                    <!-- Map 'position' to 'jobTitle' -->
                    <jobTitle><xsl:value-of select="position" /></jobTitle>
                    <!-- Map 'age' to 'yearsOld' -->
                    <yearsOld><xsl:value-of select="age" /></yearsOld>
                </staff>
            </xsl:for-each>
        </company>
    </xsl:template>

</xsl:stylesheet>
