document.addEventListener('DOMContentLoaded', () => {
  const blockchain = document.getElementById('blockchain');

  // Generate blocks
  for (let i = 845776; i <= 845900; i++) {
      const block = document.createElement('div');
      block.className = 'block';
      const text = document.createElement('p');
      text.className = 'block-content';

      text.textContent = `#${i}`;
      block.appendChild(text);
      blockchain.appendChild(block);
  }

  // Add sliding functionality
  let isDown = false;
  let startX;
  let scrollLeft;

  const slider = document.querySelector('.block-wrapper');
  
  slider.addEventListener('mousedown', (e) => {
      isDown = true;
      slider.classList.add('active');
      startX = e.pageX - slider.offsetLeft;
      scrollLeft = slider.scrollLeft;
  });

  slider.addEventListener('mouseleave', () => {
      isDown = false;
      slider.classList.remove('active');
  });

  slider.addEventListener('mouseup', () => {
      isDown = false;
      slider.classList.remove('active');
  });

  slider.addEventListener('mousemove', (e) => {
      if (!isDown) return;
      e.preventDefault();
      const x = e.pageX - slider.offsetLeft;
      const walk = (x - startX) * 3; //scroll-fast
      slider.scrollLeft = scrollLeft - walk;
  });
});
