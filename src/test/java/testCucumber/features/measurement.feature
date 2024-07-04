Feature:
  Als Laboringenieur möchte ich Messungen zu einer Messreihe durchführen
  können, um komplette Messreihen zu erhalten.

  Scenario: Erfolgreiche Messung ohne vorhandene Messung
    Given Messreihen ansehen
    Given Messreihe mit MessreihenId "1" aussuchen
    When zur aktuellen Messreihe weitere Messung durchfuehren
    Then Es wurde eine Messung zur aktuellen Messreihe an Position "1" gespeichert

  Scenario: Erfolgreiche Messung mit einer vorhandenen Messung
    Given Messreihen ansehen
    Given Messreihe mit MessreihenId "2" aussuchen
    When zur aktuellen Messreihe weitere Messung durchfuehren
    Then Es wurde eine Messung zur aktuellen Messreihe an Position "2" gespeichert

  Scenario: Erfolgreiche Messung mit zwei vorhandenen Messungen
    Given Messreihen ansehen
    Given Messreihe mit MessreihenId "3" aussuchen
    When zur aktuellen Messreihe weitere Messung durchfuehren
    Then Es wurde eine Messung zur aktuellen Messreihe an Position "3" gespeichert

  Scenario: Exception bei Messung mit einer vorhandenen Messung
    Given Messreihen ansehen
    Given Messreihe mit MessreihenId "2" aussuchen
    When zur aktuellen Messreihe zu schnell weitere Messung durchfuehren
    Then Exception zum Zeitintervall bei der Anlage einer Messung zur aktuellen Messreihe erhalten

  Scenario: Exception bei Messung mit zwei vorhandenen Messungen
    Given Messreihen ansehen
    Given Messreihe mit MessreihenId "3" aussuchen
    When zur aktuellen Messreihe zu schnell weitere Messung durchfuehren
    Then Exception zum Zeitintervall bei der Anlage einer Messung zur aktuellen Messreihe erhalten