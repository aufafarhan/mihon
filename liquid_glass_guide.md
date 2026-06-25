# Panduan Lengkap Styling Liquid Glass

Dokumen ini adalah panduan komprehensif untuk mendesain dan menata (*styling*) antarmuka menggunakan komponen `<LiquidGlass>`. Efek ini sangat bergantung pada interaksi antara elemen kaca dan elemen yang ada di belakangnya.

---

## 1. Persiapan Latar Belakang (Background)

Efek Liquid Glass **tidak akan terlihat menarik** jika diletakkan di atas warna solid (misalnya latar belakang putih polos atau hitam polos). Efek ini bekerja dengan membiaskan dan memantulkan piksel dari latar belakang.

> [!IMPORTANT]
> **Aturan Emas:** Gunakan latar belakang yang dinamis, memiliki tekstur, atau kaya warna (seperti foto, gradien warna-warni, atau animasi).

### Tips Memilih Background:
*   **High Contrast & Colorful:** Gambar dengan banyak variasi warna akan membuat efek refraksi (pembiasan) dan *chromatic aberration* (pemisahan warna RGB) terlihat jauh lebih realistis.
*   **Gunakan Pola/Tekstur:** Jika tidak memakai foto, gunakan CSS Gradient yang kompleks (seperti Mesh Gradient) atau pola geometris.
*   **Pergerakan (Motion):** Meletakkan animasi sederhana atau video di belakang komponen Liquid Glass akan memperkuat ilusi kaca yang tebal karena pembiasannya akan terus berubah-ubah.

---

## 2. Pengaturan Properti Liquid Glass (Parameter Kaca)

Anda dapat mengontrol "jenis kaca" yang ingin dibuat melalui *props* pada komponen `<LiquidGlass>`:

| Properti | Skala / Tipe | Fungsi & Pengaruh Visual |
| :--- | :--- | :--- |
| `blurAmount` | `0.0` - `1.0` | Menentukan tingkat buram kaca. Gunakan nilai tinggi (`0.5+`) jika Anda butuh keterbacaan teks yang maksimal. Gunakan nilai rendah (`0.1`) untuk kaca tipis/bening. |
| `saturation` | `100%` - `300%` | Semakin tinggi, warna *background* yang menembus kaca akan semakin menyala/terang. Berguna untuk mengimbangi efek blur yang kadang membuat warna jadi pudar. |
| `displacementScale` | `0` - `200` | Kekuatan distorsi di tepi kaca. Nilai tinggi (`100+`) membuatnya terlihat seperti gumpalan air tebal. Nilai rendah membuatnya seperti kaca pipih biasa. |
| `aberrationIntensity`| `0` - `20` | Pemisahan warna (Merah/Hijau/Biru) di pinggiran kaca. Nilai `2` sampai `5` sudah cukup untuk memberikan realisme lensa optik. |
| `elasticity` | `0.0` - `1.0` | Seberapa cair kaca merespons kursor. Gunakan `0` untuk elemen statis (seperti Card). Gunakan `0.3 - 0.5` untuk elemen interaktif (seperti Button). |
| `cornerRadius` | `0` - `100+` | Mengatur lengkungan sudut komponen. Gunakan nilai sangat besar (misal `999`) untuk bentuk pil/kapsul. |
| `overLight` | `boolean` | Jika `true`, komponen akan diberi *tint* gelap. Wajib digunakan jika kaca berada di atas *background* yang sangat terang (putih/kuning) agar teks putih tetap terbaca. |

---

## 3. Styling Konten Internal (Teks, Ikon, Layout)

Setelah kaca dibuat, Anda perlu menata konten di dalamnya (biasanya menggunakan *utility classes* seperti Tailwind CSS).

> [!TIP]
> Elemen di dalam Liquid Glass sebaiknya menggunakan warna yang kontras dengan warna keseluruhan. Karena Liquid Glass sering kali mencerahkan background, **teks berwarna putih (`text-white`) dengan bayangan (`text-shadow`)** adalah kombinasi paling aman.

### Contoh Implementasi Card
```tsx
<LiquidGlass 
  blurAmount={0.5} 
  saturation={140} 
  displacementScale={100}
  cornerRadius={32}
>
  {/* Kontainer Internal */}
  <div className="w-80 p-6 flex flex-col gap-4">
    
    {/* Teks dengan Drop Shadow agar tidak tenggelam oleh background terang */}
    <h3 className="text-2xl font-bold text-white drop-shadow-md">
      User Profile
    </h3>
    
    {/* Elemen transparan tambahan di dalam kaca */}
    <div className="bg-white/10 border border-white/20 rounded-xl p-4">
      <p className="text-white/80 text-sm">Informasi pengguna diletakkan di sini.</p>
    </div>
    
  </div>
</LiquidGlass>
```

---

## 4. Resep / *Preset* Gaya

Berikut beberapa kombinasi angka untuk menghasilkan jenis kaca yang berbeda:

### A. Frosted Glass (Kaca Es / Matte)
Fokus pada blur tinggi dan distorsi yang minim. Cocok untuk *Dashboard* atau form login.
*   `blurAmount={0.8}`
*   `saturation={110}`
*   `displacementScale={20}`
*   `aberrationIntensity={0}`

### B. Water Drop (Tetesan Cairan / Embun)
Fokus pada distorsi tinggi dan elastisitas. Sangat interaktif, cocok untuk tombol (*Call to Action*).
*   `blurAmount={0.1}`
*   `saturation={150}`
*   `displacementScale={150}`
*   `elasticity={0.6}`
*   `cornerRadius={100}`

### C. Thick Lens (Lensa Kaca Tebal)
Realistis dengan efek optik yang menonjol.
*   `blurAmount={0.3}`
*   `saturation={120}`
*   `displacementScale={80}`
*   `aberrationIntensity={10}`

---

## 5. Pertimbangan & *Best Practices*

> [!WARNING]
> **Kompabilitas Browser:** Efek refraksi tingkat lanjut pada pinggiran kaca mungkin tidak bekerja 100% sempurna di browser non-Chromium (seperti Safari atau Firefox lama). Selalu uji tampilan di berbagai browser.

*   **Performa:** Kalkulasi efek ini (terutama mode shader) menggunakan WebGL atau filter SVG kompleks. Jangan membuat elemen Liquid Glass terlalu banyak dalam satu halaman (misalnya lebih dari 10 kartu berukuran besar) karena bisa menyebabkan halaman patah-patah (*lag*).
*   **Aksesibilitas:** Pastikan rasio kontras teks selalu memadai. Manfaatkan properti `overLight={true}` jika pengguna sedang menggulir halaman ke area yang cerah/terang.
