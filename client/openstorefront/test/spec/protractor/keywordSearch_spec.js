describe('When I search for Common Map Widget API', function() {
  it('should return two results', function() {
    // Open the main site
    browser.get('http://di2e.github.io/openstorefront');
    
    // Enter the search term (changed to enter after updates to search keys 7/28)
    element(by.id('mainSearchBar')).sendKeys('Common Map Widget API', protractor.Key.ENTER);

    // Pause, momentarily, to check manually that the correct search key was pressed
    //browser.debugger();

    // Should only be two results
    expect(element.all(by.repeater('item in data')).count()).toEqual(2);


  });
});