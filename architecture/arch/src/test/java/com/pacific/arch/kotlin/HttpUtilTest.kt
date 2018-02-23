package com.pacific.arch.kotlin

import com.pacific.arch.data.*
import com.squareup.moshi.Types
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HttpUtilTest {

    @Test
    fun testToMap() {
        val note = Note(
                "how to improve yourself",
                1024L,
                "Learn more, sleep more, and eat more")
        val map1 = toMap(note)

        assertEquals("how to improve yourself", map1.getValue("note_title"))
        assertEquals(1024L, map1.getValue("note_star"))
        assertEquals("Learn more, sleep more, and eat more", map1.getValue("note_content"))
    }

    @Test
    fun testJson() {
        val access = "20170101"
        val now = System.currentTimeMillis()
        val diskEntry1 = DiskCacheEntry(str2ByteArray(access), now + 5000, now + 1000)
        val diskEntry2 = fromJson<DiskCacheEntry>(
                toJson(diskEntry1, DiskCacheEntry::class.java),
                DiskCacheEntry::class.java)

        assertEquals(diskEntry1.TTL, diskEntry2.TTL)
        assertEquals(diskEntry1.softTTL, diskEntry2.softTTL)
        assertEquals(diskEntry1.data.size, diskEntry2.data.size)
        assertEquals(diskEntry1.data[0], diskEntry2.data[0])
        assertFalse(diskEntry1.data == diskEntry2.data)


        val list1 = listOf(
                Note("note 1", 1024L, "Learn more, sleep more, and eat more-1"),
                Note("note 2", 512L, "Learn more, sleep more, and eat more-2"),
                Note("note 3", 72L, "Learn more, sleep more, and eat more-3"))

        val page1 = SimplePage(list1)
        val type1 = Types.newParameterizedType(SimplePage::class.java)
        val page2 = fromJson<SimplePage>(toJson(page1, type1), type1)
        assertTrue(page1.list.size == page2.list.size && 3 == page2.list.size)
        assertEquals("note 1", list1[0].title)
        assertEquals("Learn more, sleep more, and eat more-2", list1[1].content)
        assertEquals(72L, list1[2].star)
        assertEquals("note 1", page2.list[0].title)
        assertEquals("Learn more, sleep more, and eat more-2", page2.list[1].content)
        assertEquals(72L, page2.list[2].star)


        val page3 = Page<Note>(list1)
        val type2 = Types.newParameterizedType(Page::class.java, Note::class.java)
        val page4 = fromJson<Page<Note>>(toJson(page3, type2), type2)
        assertTrue(page3.list.size == page4.list.size && 3 == page4.list.size)
        assertEquals("note 1", page3.list[0].title)
        assertEquals("Learn more, sleep more, and eat more-2", page3.list[1].content)
        assertEquals(72L, page3.list[2].star)
        assertEquals("note 1", page4.list[0].title)
        assertEquals("Learn more, sleep more, and eat more-2", page4.list[1].content)
        assertEquals(72L, page4.list[2].star)


        val type3 = Types.newParameterizedType(List::class.java, Note::class.java)
        val list3 = fromJson<List<Note>>(toJson(list1, type3), type3)
        assertEquals("note 1", list3[0].title)
        assertEquals("Learn more, sleep more, and eat more-2", list3[1].content)
        assertEquals(72L, list3[2].star)
    }

    @Test
    fun testByArrayJson() {
        val access = "20170101"
        val now = System.currentTimeMillis()
        val diskEntry1 = DiskCacheEntry(str2ByteArray(access), now + 5000, now + 1000)
        val diskEntry2 = fromByteArrayJson<DiskCacheEntry>(
                toByteArrayJson(diskEntry1, DiskCacheEntry::class.java),
                DiskCacheEntry::class.java)

        assertEquals(diskEntry1.TTL, diskEntry2.TTL)
        assertEquals(diskEntry1.softTTL, diskEntry2.softTTL)
        assertEquals(byteArray2Str(diskEntry1.data), access)
        assertEquals(byteArray2Str(diskEntry2.data), access)
    }

    @Test
    fun testStringByArray() {
        assertEquals("hello world!", byteArray2Str(str2ByteArray("hello world!")))
    }
}